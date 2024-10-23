package es.redmic.api.privatedata.service;

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
import org.springframework.stereotype.Service;

import es.redmic.api.privatedata.repository.GeoFixedObservationSeriesESRepository;
import es.redmic.es.geodata.common.service.RGeoDataESService;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.geofixedstation.dto.GeoFixedTimeSeriesDTO;

@Service
public class GeoFixedObservationSeriesESService extends RGeoDataESService<GeoFixedTimeSeriesDTO, GeoPointData> {

	@Autowired
	public GeoFixedObservationSeriesESService(GeoFixedObservationSeriesESRepository repository) {
		super(repository);
	}
}
