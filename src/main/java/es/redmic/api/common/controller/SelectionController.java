package es.redmic.api.common.controller;

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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import es.redmic.es.common.service.SelectionService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.JSONCollectionDTO;
import es.redmic.models.es.common.dto.SelectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.model.Selection;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

@RestController
@RequestMapping(value = "**${controller.mapping.SELECTIONS}")
public class SelectionController extends RBaseController<Selection, SelectionDTO, SimpleQueryDTO> implements ISettingsController {

	// TODO: Controlar las rutas que entran para que no puedan entrar todas si no queremos

	private SelectionService service;

	@Value("${controller.mapping.SELECTIONS}")
	String selectionBaseURI;

	@Autowired
	public SelectionController(SelectionService service) {
		super(service);
		this.service = service;

	}

	@PostMapping(value = "/")
	@ResponseBody
	public SuperDTO saveSettings(@Valid @RequestBody SelectionDTO dto, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		String basePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		dto.setService(basePath.replace(selectionBaseURI + "/", ""));
		return new ElasticSearchDTO( service.save(dto), 1);
	}

	@PutMapping(value = "/{id}")
	@ResponseBody
	public SuperDTO updateSettings(@PathVariable("id") String id, @Valid @RequestBody SelectionDTO dto, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		String basePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		dto.setService(basePath.replace(selectionBaseURI + "/" + id, ""));
		return new ElasticSearchDTO(service.save(dto), 1);
	}

	@PostMapping(value = "/_search")
	@ResponseBody
	public SuperDTO findAllSettings(@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult, HttpServletRequest request) {

		processQuery(queryDTO, bindingResult);

		String basePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		JSONCollectionDTO result = service.findAll(queryDTO, basePath.replace(selectionBaseURI + "/_search", ""));
		return new ElasticSearchDTO(result, result.getTotal());
	}

	@GetMapping(value = "/{id}")
	@ResponseBody
	public SuperDTO getSettings(@PathVariable("id") String id) {

		SelectionDTO response = service.get(id.toString());
		return new ElasticSearchDTO(response, response == null ? 0 : 1);
	}

	@GetMapping(value = "/_suggest")
	@ResponseBody
	public SuperDTO suggestSettings(@RequestParam("fields") String[] fields, @RequestParam("text") String text,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = service.createSimpleQueryDTOFromSuggestQueryParams(fields, text, size);
		processQuery(queryDTO);
		List<String> response = service.suggest(convertToDataQuery(queryDTO));

		return new ElasticSearchDTO(response, response.size());
	}

	@DeleteMapping(value = "/{id}")
	@ResponseBody
	public SuperDTO deleteSettings(@PathVariable("id") String id) {

		service.delete(id);
		return new SuperDTO(true);
	}
}
