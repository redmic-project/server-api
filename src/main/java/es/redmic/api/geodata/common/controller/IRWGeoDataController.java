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

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;

public interface IRWGeoDataController<TDBModel extends LongModel, TDTO extends MetaFeatureDTO<?,?>> {

	public SuperDTO add(String activityId, TDTO dto, BindingResult errorDto);
	public SuperDTO update(TDTO dto, BindingResult errorDto, String activityId, String id, HttpServletResponse response);
	public SuperDTO delete(String id);
}
