package es.redmic.api.presence.biological.common;

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

import es.redmic.api.presence.geodata.common.GeoDataPresenceController;
import es.redmic.es.geodata.common.service.GeoPresenceESService;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public abstract class GeoBiologicalPresenceController<TModel extends Feature<GeoDataProperties, ?>, TDTO extends MetaFeatureDTO<?, ?>, TQueryDTO extends SimpleQueryDTO>
		extends GeoDataPresenceController<TModel, TDTO, TQueryDTO> {

	protected GeoBiologicalPresenceController(GeoPresenceESService<TDTO, TModel> serviceES) {
		super(serviceES);
	}
}
