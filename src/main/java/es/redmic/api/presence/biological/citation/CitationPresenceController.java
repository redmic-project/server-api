package es.redmic.api.presence.biological.citation;

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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.presence.biological.common.GeoBiologicalPresenceController;
import es.redmic.es.common.queryFactory.geodata.CitationQueryUtils;
import es.redmic.es.geodata.citation.service.CitationESService;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.citation.dto.CitationDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;

@RestController
@RequestMapping(value = "${controller.mapping.CITATIONS}")
public class CitationPresenceController extends GeoBiologicalPresenceController<GeoPointData, CitationDTO, GeoDataQueryDTO> {

	@Autowired
	public CitationPresenceController(CitationESService service) {
		super(service);
	}

	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(CitationQueryUtils.getFieldsExcludedOnQuery());
	}
}
