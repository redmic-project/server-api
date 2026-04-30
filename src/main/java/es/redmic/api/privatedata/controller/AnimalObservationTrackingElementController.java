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

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.api.privatedata.service.AnimalObservationTrackingESService;
import es.redmic.es.common.queryFactory.geodata.TrackingQueryUtils;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.dto.UuidDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.tracking.common.ElementTrackingDTO;

@RestController
@RequestMapping(value = "${controller.mapping.PRIVATE_TRACKING_ELEMENTS_BY_ACTIVITY}")
public class AnimalObservationTrackingElementController extends RBaseController<GeoPointData, ElementTrackingDTO, GeoDataQueryDTO>{

	AnimalObservationTrackingESService service;

	@Autowired
	public AnimalObservationTrackingElementController(AnimalObservationTrackingESService serviceES) {
		super(serviceES);
		this.service = serviceES;
	}

	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(TrackingQueryUtils.getFieldsExcludedOnQuery());
	}

	@GetMapping(value = "/{uuid}")
	@ResponseBody
	public SuperDTO getElement(@PathVariable("uuid") String elementUuid) {

		UuidDTO result = service.getElement(elementUuid);
		return new ElasticSearchDTO(result, result != null ? 1 : 0);
	}

	@PostMapping(value = "/_search")
	@ResponseBody
	public SuperDTO getElements(@PathVariable("activityId") String activityId,
			@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);

		return new ElasticSearchDTO(service.getElementsByActivity(activityId, queryDTO));
	}
}
