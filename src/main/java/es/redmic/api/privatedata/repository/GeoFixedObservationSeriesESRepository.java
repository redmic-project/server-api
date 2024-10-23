package es.redmic.api.privatedata.repository;

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

import org.springframework.stereotype.Repository;

import es.redmic.es.common.queryFactory.geodata.GeoFixedTimeSeriesQueryUtils;
import es.redmic.es.geodata.geofixedstation.repository.GeoFixedBaseESRepository;
import es.redmic.models.es.geojson.common.model.GeoPointData;

@Repository
public class GeoFixedObservationSeriesESRepository extends GeoFixedBaseESRepository<GeoPointData> {

	protected static String[] PRIVATE_INDEX = { "private_geodata" };
	protected static String TYPE = "_doc";

	public GeoFixedObservationSeriesESRepository() {
		super(PRIVATE_INDEX, TYPE);
		setInternalQuery(GeoFixedTimeSeriesQueryUtils.INTERNAL_QUERY);
	}

	@Override
	public String[] getIndex() {
		return PRIVATE_INDEX;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	protected String getMappingFilePath(String index, String type) {
		return MAPPING_BASE_PATH + "private/geodata" + MAPPING_FILE_EXTENSION;
	}
}
