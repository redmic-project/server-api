package es.redmic.api.geodata.geofixedstation.controller;

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
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.geodata.common.controller.RWGeoDataController;
import es.redmic.db.geodata.properties.fixedsurvey.model.FixedSurvey;
import es.redmic.db.geodata.properties.fixedsurvey.service.FixedSurveyObjectCollectingService;
import es.redmic.es.common.queryFactory.geodata.GeoFixedSeriesQueryUtils;
import es.redmic.es.geodata.geofixedstation.service.GeoFixedObjectCollectingSeriesESService;
import es.redmic.models.es.common.DataPrefixType;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoLineStringData;
import es.redmic.models.es.geojson.geofixedstation.dto.GeoFixedObjectCollectingSeriesDTO;

@RestController
@RequestMapping(value = "${controller.mapping.OBJECTCOLLECTING_BY_ACTIVITY}")
public class GeoFixedObjectCollectingSeriesController
	extends RWGeoDataController<FixedSurvey, GeoLineStringData, GeoFixedObjectCollectingSeriesDTO, GeoDataQueryDTO>{

	@Autowired
	public GeoFixedObjectCollectingSeriesController(FixedSurveyObjectCollectingService service, GeoFixedObjectCollectingSeriesESService serviceES) {
		super(service, serviceES);
	}

	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(GeoFixedSeriesQueryUtils.getFieldsExcludedOnQuery());
	}

	@Override
	public SuperDTO add(@PathVariable("activityId") String activityId, @Valid @RequestBody GeoFixedObjectCollectingSeriesDTO dto,
			BindingResult errorDto) {
		dto.getProperties().getSite().setPrefixType(DataPrefixType.OBJECT_COLLECTING);
		return super.add(activityId, dto, errorDto);
	}

	@Override
	public SuperDTO update(@Valid @RequestBody GeoFixedObjectCollectingSeriesDTO dto, BindingResult errorDto,
			@PathVariable("activityId") String activityId, @PathVariable("id") String id, HttpServletResponse response) {

		dto.getProperties().getSite().setPrefixType(DataPrefixType.OBJECT_COLLECTING);
		return super.update(dto, errorDto, activityId, id, response);
	}
}
