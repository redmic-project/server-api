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

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.MgetDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public interface IRGeoDataController<TModel extends Feature<GeoDataProperties, ?>, TDTO extends MetaFeatureDTO<?,?>, TQueryDTO extends SimpleQueryDTO> {

	public SuperDTO findById(String activityId, String id);
	public SuperDTO _mget(String activityId, MgetDTO mgetDto, BindingResult errorDto);
	public SuperDTO _search(String activityId, @RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size);
	public SuperDTO _advancedSearch(String activityId, TQueryDTO query, BindingResult errorDto);
	public SuperDTO _suggest(String activityId, String[] fields, String text, Integer size);
}
