package es.redmic.api.privatedata.controller;

/*-
 * #%L
 * API
 * %%
 * Copyright (C) 2024 REDMIC Project / Server
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.geodata.common.controller.RGeoDataController;
import es.redmic.api.privatedata.service.GeoFixedObservationSeriesESService;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.geofixedstation.dto.GeoFixedTimeSeriesDTO;

@RestController
@RequestMapping("${controller.mapping.PRIVATE_SURVEYSTATIONS_BY_ACTIVITY}")
public class GeoFixedObservationSeriesController
	extends RGeoDataController<GeoPointData, GeoFixedTimeSeriesDTO, GeoDataQueryDTO> {

	@Autowired
	public GeoFixedObservationSeriesController(GeoFixedObservationSeriesESService ESService) {
		super(ESService);
	}
}
