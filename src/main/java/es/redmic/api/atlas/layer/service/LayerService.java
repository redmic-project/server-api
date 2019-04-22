package es.redmic.api.atlas.layer.service;

/*-
 * #%L
 * API
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.geotools.ows.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import es.redmic.db.atlas.layer.model.Layer;
import es.redmic.db.atlas.layer.repository.LayerRepository;
import es.redmic.db.common.service.ServiceRWImpl;
import es.redmic.es.atlas.service.LayerESService;
import es.redmic.exception.custom.ResourceNotFoundException;
import es.redmic.exception.database.DBNotFoundException;
import es.redmic.mediastorage.service.MediaStorageServiceItfc;
import es.redmic.models.es.atlas.dto.HierarchyLayersDTO;
import es.redmic.models.es.atlas.dto.LayerCategoryDTO;
import es.redmic.models.es.atlas.dto.LayerCompactDTO;
import es.redmic.models.es.atlas.dto.LayerDTO;
import es.redmic.models.es.atlas.dto.ProtocolsDTO;
import es.redmic.models.es.atlas.dto.ThemeInspireDTO;
import es.redmic.models.es.atlas.model.LayerModel;
import es.redmic.models.es.common.dto.UrlDTO;
import es.redmic.utils.geo.wms.GetCapabilities;

@Service
public class LayerService extends ServiceRWImpl<Layer, LayerDTO> {

	@Value("${property.path.media_storage.LAYERS}")
	private String PATH_LAYERS;

	@Value("${property.URL_LAYERS_MEDIASTORAGE}")
	private String URL_LAYERS;

	private String IMAGE_FORMAT = "image/png";

	@Autowired
	MediaStorageServiceItfc mediaStorageService;

	@Autowired
	LayerESService layerESService;

	LayerRepository repository;

	@Autowired
	public LayerService(LayerRepository repository) {
		super(repository);
		this.repository = repository;
	}

	public List<LayerDTO> save(UrlDTO workSpace) {

		HashMap<String, LayerDTO> layers = getCapabilitiesFromGeoServer(workSpace.getUrl());

		List<LayerDTO> layersDTO = new ArrayList<LayerDTO>();

		for (Entry<String, LayerDTO> entry : layers.entrySet()) {
			if (entry.getValue() != null) {
				LayerDTO layerDTO = entry.getValue();

				Layer model = repository.findByNameAndUrlSource(layerDTO.getName(), layerDTO.getUrlSource());
				if (model == null) {
					layerDTO.prepareDTO();
					model = repository.save(convertDtoToModel(layerDTO));
					factory.getMapperFacade().map(model, layerDTO);
					publish(ADD_EVENT, layerDTO);
					layersDTO.add(layerDTO);
				} else {
					layerDTO.setId(model.getId());
					layersDTO.add(executeUpdate(layerDTO));
				}
			}
		}

		return layersDTO;
	}

	/**
	 * Modifica una layer pero enviando datos para completarla y actualizando el
	 * resto del geoserver
	 * 
	 * @param layerCompactDTO
	 *            Dto con los datos básicos de la layer a actualizar
	 *
	 * @param id
	 *            Identificador de la layer a actualizar
	 * 
	 * @param path
	 *            Path de la imagen de la layer
	 * 
	 * @return Dto de layer con los datos actualizados
	 */
	public LayerDTO update(LayerCompactDTO layerCompactDTO, Long id) {

		LayerModel layer = layerESService.findById(String.valueOf(id));

		if (layer != null) {

			HashMap<String, LayerDTO> layers = getCapabilitiesFromGeoServer(layer.getUrlSource());

			String layerName = layer.getName();

			if (layers.get(layerName) != null) {

				LayerDTO layerDTO = layers.get(layerName);
				layerDTO.setId(id);

				layerDTO.setParent(layerCompactDTO.getParent());
				layerDTO.setLatLonBoundsImage(layerCompactDTO.getLatLonBoundsImage());
				layerDTO.setProtocols(layerCompactDTO.getProtocols());
				layerDTO.setAlias(layerCompactDTO.getAlias());
				layerDTO.setAtlas(layerCompactDTO.getAtlas());
				layerDTO.setDescription(layerCompactDTO.getDescription());
				layerDTO.setThemeInspire(layerCompactDTO.getThemeInspire());

				if (!layerDTO.getLatLonBoundsImage().equals(layer.getLatLonBoundsImage()))
					layerDTO.setImage(loadImageLayer(layerDTO));

				return executeUpdate(layerDTO);
			}
		}

		throw new DBNotFoundException("layer", String.valueOf(id));
	}

	/**
	 * Modifica una layer actualizando solo los datos del geoserver sin
	 * modificar los datos propios
	 * 
	 * @param id
	 *            Identificador de la layer a refrescar
	 * 
	 * @return Dto de layer con los datos actualizados
	 */
	public LayerDTO refreshLayer(Long id) {

		LayerModel layer = (LayerModel) layerESService.findById(String.valueOf(id));

		if (layer != null) {

			HashMap<String, LayerDTO> layers = getCapabilitiesFromGeoServer(layer.getUrlSource());

			String layerName = layer.getName();

			if (layers.get(layerName) != null) {

				LayerDTO layerDTO = (LayerDTO) layers.get(layerName);
				layerDTO.setId(id);

				if (layer.getParentId() != null)
					layerDTO.setParent(getParent(layer.getParentId()));

				layerDTO.setLatLonBoundsImage(layer.getLatLonBoundsImage());
				layerDTO.setProtocols(factory.getMapperFacade().mapAsList(layer.getProtocols(), ProtocolsDTO.class));
				layerDTO.setImage(layer.getImage());
				layerDTO.setAlias(layer.getAlias());
				layerDTO.setAtlas(layer.getAtlas());
				layerDTO.setDescription(layer.getDescription());
				layerDTO.setThemeInspire(factory.getMapperFacade().map(layer.getThemeInspire(), ThemeInspireDTO.class));
				return executeUpdate(layerDTO);
			}
		}
		throw new DBNotFoundException("layer", String.valueOf(id));
	}

	private LayerDTO executeUpdate(LayerDTO layerDTO) {

		layerDTO.prepareDTO();
		Layer model = convertDtoToModel(layerDTO);
		model = updateModel(model);
		factory.getMapperFacade().map(model, layerDTO);
		publish(UPDATE_EVENT, layerDTO);

		return layerDTO;
	}

	private HashMap<String, LayerDTO> getCapabilitiesFromGeoServer(String urlSource) {

		GetCapabilities getCapabilities;
		HashMap<String, LayerDTO> layers = null;
		try {
			getCapabilities = new GetCapabilities(urlSource);
			layers = getCapabilities.getCapabilities();
		} catch (ServiceException | IOException e) {
			throw new ResourceNotFoundException(e);
		}
		return layers;
	}

	private String loadImageLayer(LayerDTO layer) {

		if (layer.getLatLonBoundsImage() == null)
			return null;

		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new NameValuePair("service", "WMS"));
		qparams.add(new NameValuePair("version", "1.1.0"));
		qparams.add(new NameValuePair("request", "GetMap"));
		qparams.add(new NameValuePair("layers", layer.getName()));

		String bounds = String.valueOf(layer.getLatLonBoundsImage().getMinX());
		bounds += "," + String.valueOf(layer.getLatLonBoundsImage().getMinY());
		bounds += "," + String.valueOf(layer.getLatLonBoundsImage().getMaxX());
		bounds += "," + String.valueOf(layer.getLatLonBoundsImage().getMaxY());
		qparams.add(new NameValuePair("bbox", bounds));

		qparams.add(new NameValuePair("width", "256"));
		qparams.add(new NameValuePair("height", "256"));
		qparams.add(new NameValuePair("format", IMAGE_FORMAT));

		HttpMethod method = new GetMethod(layer.getUrlSource());
		method.setQueryString(qparams.toArray(new NameValuePair[] {}));

		HttpClient client = new HttpClient();

		String fileName = layer.getId() + "." + IMAGE_FORMAT.split("/")[1];
		try {
			client.executeMethod(method);
			mediaStorageService.upload(method.getResponseBody(), IMAGE_FORMAT, PATH_LAYERS, fileName);
		} catch (IOException e) {
			throw new ResourceNotFoundException(e);
		} finally {
			method.releaseConnection();
		}

		return URL_LAYERS + fileName;
	}

	public List<LayerDTO> updateHierarchyLayers(HierarchyLayersDTO dto) {

		List<LayerDTO> layersDTO = new ArrayList<LayerDTO>();

		for (int i = 0; i < dto.getLayers().size(); i++) {
			LayerDTO layerDTO = layerESService.get(String.valueOf(dto.getLayers().get(i).getId()));
			layerDTO.setParent(getParent(dto.getLayers().get(i).getParentId()));
			Layer result = updateModel(convertDtoToModel(layerDTO));
			layerDTO.setPath(result.getPath());
			publish(UPDATE_EVENT, layerDTO);
			layersDTO.add(layerDTO);
		}

		return layersDTO;
	}

	public LayerDTO addCategoryLayers(LayerCategoryDTO dto) {

		LayerDTO layerDTO = factory.getMapperFacade().map(dto, LayerDTO.class);
		layerDTO.setAlias(dto.getName());
		Layer result = repository.save(convertDtoToModel(layerDTO));
		layerDTO.setId(result.getId());
		layerDTO.setPath(result.getPath());
		publish(ADD_EVENT, layerDTO);

		return layerDTO;
	}

	public LayerDTO updateCategoryLayers(LayerCategoryDTO dto, Long id) {

		LayerModel layer = layerESService.findById(String.valueOf(id));

		LayerDTO layerDTO = factory.getMapperFacade().map(dto, LayerDTO.class);
		layerDTO.setAlias(dto.getName());

		if (layer != null) {
			layerDTO.setId(id);

			if (dto.getParent() != null)
				layerDTO.setParent(dto.getParent());
			else if (layer.getParentId() != null)
				layerDTO.setParent(getParent(layer.getParentId()));
		}
		Layer result = updateModel(convertDtoToModel(layerDTO));
		layerDTO.setPath(result.getPath());
		publish(UPDATE_EVENT, layerDTO);

		return layerDTO;
	}

	private LayerDTO getParent(Long parentId) {

		LayerDTO parent = new LayerDTO();
		parent.setId(parentId);
		return parent;
	}
}
