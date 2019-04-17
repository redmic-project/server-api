package es.redmic.api.atlas.layer.controller;

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

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.atlas.layer.service.LayerService;
import es.redmic.api.common.controller.RController;
import es.redmic.api.common.service.JsonSchemaService;
import es.redmic.es.atlas.service.LayerESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.atlas.dto.HierarchyLayersDTO;
import es.redmic.models.es.atlas.dto.LayerCategoryDTO;
import es.redmic.models.es.atlas.dto.LayerCompactDTO;
import es.redmic.models.es.atlas.dto.LayerDTO;
import es.redmic.models.es.atlas.model.LayerModel;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.BodyListDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.dto.UrlDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.LAYER}")
public class RWLayerController extends RController<LayerModel, LayerDTO, GeoDataQueryDTO> {
	
	@Autowired
	JsonSchemaService jsonSchemaService;
	
	LayerService service;
	
	LayerESService serviceES;
	
	@Autowired
	public RWLayerController(LayerService service, LayerESService serviceES) {
		super(serviceES);
		this.service = service;
		this.serviceES = serviceES;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO saveLayers(@Valid @RequestBody UrlDTO workSpace, BindingResult errorDto) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		return new BodyListDTO<LayerDTO>(service.save(workSpace));
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO updateLayer(@Valid @RequestBody LayerCompactDTO layerCompact, BindingResult errorDto,
			@PathVariable("id") Long id) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		return new BodyItemDTO<LayerDTO>(service.update(layerCompact, id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public SuperDTO delete(@PathVariable("id") Long id) {
		service.delete(id);
		return new SuperDTO(true);
	}
	
	@RequestMapping(value = "/refresh/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO refreshLayer(@PathVariable("id") Long id) {

		return new BodyItemDTO<LayerDTO>(service.refreshLayer(id));
	}
	
	
	@RequestMapping(value = "/hierarchyUpdate/", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO updateHierarchyLayers(@Valid @RequestBody HierarchyLayersDTO dto, BindingResult errorDto) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		return new BodyListDTO<LayerDTO>(service.updateHierarchyLayers(dto));
	}
	
	@RequestMapping(value = "/category/", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO addCategoryLayers(@Valid @RequestBody LayerCategoryDTO dto, BindingResult errorDto) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		return new BodyItemDTO<LayerDTO>(service.addCategoryLayers(dto));
	}
	
	@RequestMapping(value = "/category/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO updateCategoryLayers(@Valid @RequestBody LayerCategoryDTO dto, BindingResult errorDto,
			@PathVariable("id") Long id) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		return new BodyItemDTO<LayerDTO>(service.updateCategoryLayers(dto, id));
	}

	@RequestMapping(value = "${controller.mapping.EDIT_SCHEMA}", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String,Object> getJsonSchema(HttpServletResponse response) {
		return jsonSchemaService.getJsonSchema(LayerCompactDTO.class.getName());
	}
}
