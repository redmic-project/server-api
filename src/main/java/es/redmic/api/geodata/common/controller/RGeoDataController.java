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

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.es.geodata.common.service.RGeoDataESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.MgetDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.common.dto.CategoryListDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public abstract class RGeoDataController<TModel extends Feature<GeoDataProperties, ?>, TDTO extends MetaFeatureDTO<?, ?>, TQueryDTO extends SimpleQueryDTO>
		extends RBaseController<TModel, TDTO, TQueryDTO> implements IRGeoDataController<TModel, TDTO, TQueryDTO> {

	protected RGeoDataESService<TDTO, TModel> service;

	protected RGeoDataController(RGeoDataESService<TDTO, TModel> service) {
		super(service);
		this.service = service;
	}

	@GetMapping(value = "/{id}")
	@ResponseBody
	public SuperDTO findById(@PathVariable("activityId") String activityId, @PathVariable("id") String id) {

		TDTO item = (TDTO) service.get(id, activityId);
		return new ElasticSearchDTO(item, item != null && item.getId() != null ? 1 : 0);
	}

	@PostMapping(value = "/_mget")
	@ResponseBody
	public SuperDTO _mget(@PathVariable("activityId") String activityId, @Valid @RequestBody MgetDTO mgetDto,
			BindingResult errorDto) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		return new ElasticSearchDTO(service.mget(mgetDto, activityId));
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "")
	@ResponseBody
	public SuperDTO _search(@PathVariable("activityId") String activityId, @RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = service.createSimpleQueryDTOFromTextQueryParams(from, size);
		processQuery((TQueryDTO) queryDTO);
		return new ElasticSearchDTO(service.find(convertToGeoDataQuery((TQueryDTO) queryDTO), activityId));
	}

	@PostMapping(value = "/_search")
	@ResponseBody
	public SuperDTO _advancedSearch(@PathVariable("activityId") String activityId, @Valid @RequestBody TQueryDTO queryDTO,
			BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);

		return new ElasticSearchDTO(service.find(convertToGeoDataQuery(queryDTO), activityId));
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/_suggest")
	@ResponseBody
	public SuperDTO _suggest(@PathVariable("activityId") String activityId, @RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam("text") String text, @RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = service.createSimpleQueryDTOFromSuggestQueryParams(fields, text, size);
		processQuery((TQueryDTO) queryDTO);
		List<String> response = service.suggest(activityId, convertToGeoDataQuery((TQueryDTO)queryDTO));

		return new ElasticSearchDTO(response, response.size());
	}

	@PostMapping(value = "/_suggest")
	@ResponseBody
	public SuperDTO _suggest(@PathVariable("activityId") String activityId, @Valid @RequestBody TQueryDTO queryDTO,
			BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);

		List<String> response = service.suggest(activityId, convertToGeoDataQuery(queryDTO));

		return new ElasticSearchDTO(response, response.size());
	}

	@PostMapping(value = "/_categories")
	@ResponseBody
	public SuperDTO _getCategories(@PathVariable("activityId") String activityId, @Valid @RequestBody TQueryDTO queryDTO,
			BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);

		CategoryListDTO response = service.getCategories(activityId, convertToGeoDataQuery(queryDTO));

		return new ElasticSearchDTO(response, response.size());
	}
}
