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

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.es.data.common.service.RDataESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.BaseDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.ElasticSearchNotNullDTO;
import es.redmic.models.es.common.dto.JSONCollectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.model.BaseES;
import es.redmic.models.es.common.query.dto.MgetDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

public abstract class RController<TModel extends BaseES<?>, TDTO extends BaseDTO<?>, TQueryDTO extends SimpleQueryDTO>
		extends SelectionWorkController<TModel, TDTO, TQueryDTO> implements IRController<TModel, TDTO, TQueryDTO> {

	protected RDataESService<TModel, TDTO> ESService;

	protected RController(RDataESService<TModel, TDTO> service) {
		super(service);
		ESService = service;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "")
	@ResponseBody
	public SuperDTO _search(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size,
			@RequestParam(required = false, value = "returnFields") String[] returnFields) {

		SimpleQueryDTO queryDTO = ESService.createSimpleQueryDTOFromTextQueryParams(fields, text, from, size, returnFields);
		processQuery((TQueryDTO) queryDTO);

		JSONCollectionDTO result = ESService.find(convertToDataQuery((TQueryDTO) queryDTO));

		return new ElasticSearchNotNullDTO(result, result.getTotal());
	}

	@PostMapping(value = "/_search")
	@ResponseBody
	public SuperDTO _advancedSearch(@Valid @RequestBody TQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);

		JSONCollectionDTO result = ESService.find(convertToDataQuery(queryDTO));
		return new ElasticSearchDTO(result, result.getTotal());
	}

	@PostMapping(value = "/_mget")
	@ResponseBody
	public SuperDTO _mget(@Valid @RequestBody MgetDTO dto, BindingResult errorDto) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		JSONCollectionDTO result = ESService.mget(dto);
		return new ElasticSearchDTO(result, result.getTotal());
	}

	@GetMapping(value = "/{id}")
	@ResponseBody
	public SuperDTO _get(@PathVariable("id") Long id) {

		TDTO response = ESService.get(id.toString());
		return new ElasticSearchDTO(response, response == null ? 0 : 1);
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/_suggest")
	@ResponseBody
	public SuperDTO _suggest(@RequestParam(required = false, value = "fields") String[] fields, @RequestParam("text") String text,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = ESService.createSimpleQueryDTOFromSuggestQueryParams(fields, text, size);
		processQuery((TQueryDTO) queryDTO);
		List<String> response = ESService.suggest(convertToDataQuery((TQueryDTO) queryDTO));
		return new ElasticSearchDTO(response, response.size());
	}

	@PostMapping(value = "/_suggest")
	@ResponseBody
	public SuperDTO _advancedSuggest(@Valid @RequestBody TQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);
		List<String> response = ESService.suggest(convertToDataQuery((TQueryDTO) queryDTO));
		return new ElasticSearchDTO(response, response.size());
	}
}
