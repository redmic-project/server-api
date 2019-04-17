package es.redmic.api.geodata.common.controller;

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

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.api.common.service.JsonSchemaService;
import es.redmic.db.geodata.common.model.GeoDataModel;
import es.redmic.db.geodata.common.service.ServiceGeo;
import es.redmic.es.geodata.common.service.GeoDataESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public class RWGeoDataController<TDBModel extends GeoDataModel, TModel extends Feature<GeoDataProperties, ?>, TDTO extends MetaFeatureDTO<?, ?>, TQueryDTO extends SimpleQueryDTO>
		extends RGeoDataController<TModel, TDTO, TQueryDTO> implements IRWGeoDataController<TDBModel, TDTO> {

	private ServiceGeo<TDBModel, TDTO> service;

	@Autowired
	protected JsonSchemaService jsonSchemaService;

	@Value("${property.path.media_storage.JSONSCHEMA}")
	private String jsonschemaPath;

	private Class<TDTO> typeOfTDTO;

	@SuppressWarnings("unchecked")
	public RWGeoDataController(ServiceGeo<TDBModel, TDTO> service, GeoDataESService<TDTO, TModel> serviceES) {
		super(serviceES);
		this.service = service;
		this.typeOfTDTO = (Class<TDTO>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[2];
	}

	@Override
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO add(@PathVariable("activityId") String activityId, @Valid @RequestBody TDTO dto,
			BindingResult errorDto) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		service.checkDataType(activityId);

		dto.getProperties().setActivityId(activityId);
		return new BodyItemDTO<TDTO>(service.save(dto));
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO update(@Valid @RequestBody TDTO dto, BindingResult errorDto,
			@PathVariable("activityId") String activityId, @PathVariable("id") String id,
			HttpServletResponse response) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		service.checkDataType(activityId);

		dto.setUuid(id);
		dto.getProperties().setActivityId(activityId);

		return new BodyItemDTO<TDTO>(service.update(dto));
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public SuperDTO delete(@PathVariable("id") String id) {
		service.delete(id);
		return new SuperDTO(true);
	}

	@RequestMapping(value = "${controller.mapping.EDIT_SCHEMA}", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> getJsonSchema(HttpServletResponse response) {

		return jsonSchemaService.getJsonSchema(typeOfTDTO.getName());
	}
}
