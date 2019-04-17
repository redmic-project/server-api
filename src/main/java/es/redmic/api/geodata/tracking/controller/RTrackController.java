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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.es.common.queryFactory.geodata.TrackingQueryUtils;
import es.redmic.es.geodata.tracking.common.service.TrackingESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.MgetDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.tracking.common.ElementTrackingDTO;

@RestController
@RequestMapping(value = "${controller.mapping.TRACKING_BY_ACTIVITY_AND_ELEMENT}")
public class RTrackController extends RBaseController<GeoPointData, ElementTrackingDTO, DataQueryDTO> {

	TrackingESService service;

	@Autowired
	public RTrackController(TrackingESService serviceES) {
		super(serviceES);
		this.service = serviceES;
	}

	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(TrackingQueryUtils.getFieldsExcludedOnQuery());
	}

	@RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO findById(@PathVariable("activityId") String activityId, @PathVariable("uuid") String uuid) {
		
		ElementTrackingDTO result = service.get(uuid, activityId);
		return new ElasticSearchDTO(result, result != null ? 1 : 0);
	}

	@RequestMapping(value = "/_mget", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _mget(@PathVariable("activityId") String activityId, @Valid @RequestBody MgetDTO mgetDto,
			BindingResult errorDto) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		return new ElasticSearchDTO(service.mget(mgetDto, activityId));
	}

	@RequestMapping(value = "/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _search(@PathVariable("activityId") String activityId, @PathVariable("uuid") String uuid,
			@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);

		return new ElasticSearchDTO(service.find(activityId, uuid, queryDTO));
	}

	@RequestMapping(value = "${controller.mapping.TRACK_CLUSTER}/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO getCluster(@PathVariable("activityId") String activityId, @PathVariable("uuid") String uuid,
			@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);

		return new ElasticSearchDTO(service.getTrackingPointsInLineStringCluster(activityId, queryDTO, uuid));
	}
}
