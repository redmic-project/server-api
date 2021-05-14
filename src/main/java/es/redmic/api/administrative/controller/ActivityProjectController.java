package es.redmic.api.administrative.controller;

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

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.es.administrative.service.ActivityESService;
import es.redmic.models.es.administrative.dto.ActivityDTO;
import es.redmic.models.es.administrative.model.Activity;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ACTIVITY_BY_PROJECT}")
public class ActivityProjectController extends RBaseController<Activity, ActivityDTO, DataQueryDTO>{

	ActivityESService service;

	@Autowired
	public ActivityProjectController(ActivityESService service) {
		super(service);
		this.service = service;
	}

	@PostMapping(value = "/_search")
	@ResponseBody
	public SuperDTO findAll(@PathVariable("projectId") Long projectId,
			@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult) {

		queryDTO.addTerm("path.split", projectId);

		processQuery(queryDTO, bindingResult);

		return new ElasticSearchDTO(service.find(queryDTO));
	}

	@GetMapping(value = "/_suggest")
	@ResponseBody
	public SuperDTO _suggest(@PathVariable("projectId") Long projectId, @RequestParam("fields") String[] fields,
			@RequestParam("text") String text,
			@RequestParam(required = false, value = "size") Integer size) {


		SimpleQueryDTO queryDTO = service.createSimpleQueryDTOFromSuggestQueryParams(fields, text, size);
		processQuery((DataQueryDTO) queryDTO);
		queryDTO.addTerm("path.split", projectId);

		List<String> response = service.suggest(convertToDataQuery((DataQueryDTO)queryDTO));

		return new ElasticSearchDTO(response, response.size());
	}

	@GetMapping(value = "/{id}")
	@ResponseBody
	public SuperDTO findById(@PathVariable("projectId") String projectId, @PathVariable("id") String id) {

		return new BodyItemDTO<ActivityDTO>(service.get(id));
	}
}
