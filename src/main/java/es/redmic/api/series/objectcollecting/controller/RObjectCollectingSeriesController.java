package es.redmic.api.series.objectcollecting.controller;

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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.series.common.controller.RSeriesController;
import es.redmic.es.series.objectcollecting.service.ObjectCollectingSeriesESService;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.AggsPropertiesDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.series.objectcollecting.dto.ObjectCollectingSeriesDTO;
import es.redmic.models.es.series.objectcollecting.model.ObjectCollectingSeries;

@RestController
@RequestMapping(value = "${controller.mapping.OBJECTCOLLECTING}")
public class RObjectCollectingSeriesController extends RSeriesController<ObjectCollectingSeries, ObjectCollectingSeriesDTO, GeoDataQueryDTO> {

	private ObjectCollectingSeriesESService service;

	@Autowired
	public RObjectCollectingSeriesController(ObjectCollectingSeriesESService service) {
		super(service);
		this.service = service;
	}

	@PostMapping(value = "${controller.mapping.OBJECT_CLASSIFICATION_LIST}/_search")
	@ResponseBody
	public SuperDTO findClassificationList(@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult) {

		AggsPropertiesDTO agg = new AggsPropertiesDTO();
		agg.setField("classificationList");
		queryDTO.addAgg(agg);
		queryDTO.setSize(0);

		processQuery(queryDTO, bindingResult);

		return service.findClassificationList(queryDTO);
	}

	@PostMapping(value = "${controller.mapping.OBJECT_CLASSIFICATION}/_search")
	@ResponseBody
	public SuperDTO findClassification(@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult) {

		AggsPropertiesDTO agg = new AggsPropertiesDTO();
		agg.setField("classification");
		queryDTO.addAgg(agg);
		queryDTO.setSize(0);

		processQuery(queryDTO, bindingResult);

		return service.findClassificationStatistics(queryDTO);
	}

	@PostMapping(value = "${controller.mapping.SERIES_TEMPORALDATA}/_search")
	@ResponseBody
	public SuperDTO findTemporalData(@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult) {

		AggsPropertiesDTO agg = new AggsPropertiesDTO();
		agg.setField("temporaldata");
		queryDTO.addAgg(agg);

		queryDTO.setSize(0);

		processQuery(queryDTO, bindingResult);

		return service.findTemporalDataStatistics(queryDTO);
	}
}
