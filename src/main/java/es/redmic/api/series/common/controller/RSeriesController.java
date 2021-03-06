package es.redmic.api.series.common.controller;

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

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.es.series.common.service.RSeriesESService;
import es.redmic.exception.custom.ResourceNotFoundException;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.JSONCollectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.series.common.dto.SeriesBaseDTO;
import es.redmic.models.es.series.common.model.SeriesBase;

public abstract class RSeriesController<TModel extends SeriesBase, TDTO extends SeriesBaseDTO, TQueryDTO extends SimpleQueryDTO>
		extends RBaseController<TModel, TDTO, TQueryDTO> {

	protected RSeriesESService<TModel, TDTO> ESService;

	public RSeriesController(RSeriesESService<TModel, TDTO> service) {
		super(service);
		ESService = service;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _search(@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = ESService.createSimpleQueryDTOFromQueryParams(from, size);
		processQuery((TQueryDTO) queryDTO);
		return new ElasticSearchDTO(ESService.find(convertToQuery((TQueryDTO) queryDTO)));
	}

	@RequestMapping(value = "/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _advancedSearch(@Valid @RequestBody TQueryDTO queryDTO, BindingResult errorDto) {

		processQuery(queryDTO, errorDto);

		JSONCollectionDTO result = ESService.find(convertToQuery(queryDTO));
		return new ElasticSearchDTO(result, result.getTotal());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _get(@PathVariable("id") String id) {

		TDTO result = ESService.searchById(id);
		return new ElasticSearchDTO(result, result == null ? 0 : 1);
	}

	@RequestMapping(value = "/_suggest", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _suggest() {

		throw new ResourceNotFoundException();
	}
}
