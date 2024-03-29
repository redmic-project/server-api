package es.redmic.api.presence.geodata.common;

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
import es.redmic.es.geodata.common.service.GeoDataESService;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public abstract class GeoDataPresenceController<TModel extends Feature<GeoDataProperties, ?>, TDTO extends MetaFeatureDTO<?, ?>, TQueryDTO extends SimpleQueryDTO>
		extends RBaseController<TModel, TDTO, TQueryDTO> {

	private GeoDataESService<TDTO, TModel> serviceES;

	protected GeoDataPresenceController(GeoDataESService<TDTO, TModel> serviceES) {
		super(serviceES);
		this.serviceES = serviceES;
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "")
	@ResponseBody
	public SuperDTO _search(@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = serviceES.createSimpleQueryDTOFromTextQueryParams(from, size);
		processQuery((TQueryDTO) queryDTO);
		return new ElasticSearchDTO(serviceES.find(convertToGeoDataQuery((TQueryDTO) queryDTO)));
	}

	@PostMapping(value = "/_search")
	@ResponseBody
	public SuperDTO getFeatures(@Valid @RequestBody TQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);
		return new ElasticSearchDTO(serviceES.find(convertToGeoDataQuery(queryDTO)));
	}

	@GetMapping(value = "/{id}")
	@ResponseBody
	public SuperDTO findById(@PathVariable("id") String id) {
		TDTO response = serviceES.searchById(id);
		return new ElasticSearchDTO(response, response == null ? 0 : 1);
	}

	@PostMapping(value = "/_suggest")
	@ResponseBody
	public SuperDTO _advancedSuggest(@Valid @RequestBody TQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);
		List<String> response = serviceES.suggest(convertToGeoDataQuery(queryDTO));
		return new ElasticSearchDTO(response, response.size());
	}
}
