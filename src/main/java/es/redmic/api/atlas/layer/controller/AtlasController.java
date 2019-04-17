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

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RController;
import es.redmic.es.atlas.service.LayerESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.atlas.dto.LayerDTO;
import es.redmic.models.es.atlas.model.LayerModel;
import es.redmic.models.es.common.dto.SelectionWorkDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ATLAS}")
public class AtlasController extends RController<LayerModel, LayerDTO, GeoDataQueryDTO> {
	
	LayerESService serviceES;
	
	@Autowired
	public AtlasController(LayerESService serviceES) {
		super(serviceES);
		this.serviceES = serviceES;
	}
	
	@PostConstruct
	private void postConstruct() {
		setFixedQuery("atlas", "true");
	}
	
	/**
	 * 
	 * Sobreescribe, guarda y edita selectionWork para adaptarlo a selección jerárquica
	 * 
	 **/
	@Override
	@RequestMapping(value = "${controller.mapping.SELECTION_WORK}", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO saveSelectionWork(@Valid @RequestBody SelectionWorkDTO dto, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);
		
		dto.setIdProperty("path");
		if (dto.getIds() != null && dto.getIds().size() > 0)
			dto.addIds(serviceES.getDescendantsPaths(dto.getIds()));

		return super.saveSelectionWork(dto, bindingResult, request);
	}

	@RequestMapping(value = "${controller.mapping.SELECTION_WORK}/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO updateSelection(@Valid @RequestBody SelectionWorkDTO dto, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);
		
		dto.setIdProperty("path");
		
		if (dto.getIds() != null && dto.getIds().size() > 0)
			dto.addIds(serviceES.getDescendantsPaths(dto.getIds()));
		
		return super.updateSelection(dto, bindingResult, request);
	}
}
