package es.redmic.api.geodata.tracking.controller;

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

import es.redmic.api.common.controller.RController;
import es.redmic.es.administrative.service.ActivityESService;
import es.redmic.es.common.queryFactory.geodata.TrackingQueryUtils;
import es.redmic.models.es.administrative.dto.ActivityDTO;
import es.redmic.models.es.administrative.model.Activity;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ACTIVITY_TRACKING}")
public class ActivityTrackingController extends RController<Activity, ActivityDTO, GeoDataQueryDTO> {

	@Autowired
	public ActivityTrackingController(ActivityESService service) {
		super(service);
	}

	@PostConstruct
	private void postConstruct() {

		setFixedQuery(TrackingQueryUtils.getActivityCategoryTermQuery());
	}
}
