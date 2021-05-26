package es.redmic.api.geodata.isolines.controller;

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

import es.redmic.api.geodata.common.controller.RWGeoDataController;
import es.redmic.db.geodata.isolines.model.Isolines;
import es.redmic.db.geodata.isolines.service.IsolinesService;
import es.redmic.es.common.queryFactory.geodata.IsolinesQueryUtils;
import es.redmic.es.geodata.isolines.service.IsolinesESService;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoMultiLineStringData;
import es.redmic.models.es.geojson.isolines.dto.IsolinesDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ISOLINES_BY_ACTIVITY}")
public class IsolinesController
		extends RWGeoDataController<Isolines, GeoMultiLineStringData, IsolinesDTO, GeoDataQueryDTO> {

	@Autowired
	public IsolinesController(IsolinesService service, IsolinesESService ESService) {
		super(service, ESService);
	}

	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(IsolinesQueryUtils.getFieldsExcludedOnQuery());
	}
}
