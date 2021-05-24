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

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.model.Activity;
import es.redmic.db.administrative.service.ActivityService;
import es.redmic.es.administrative.service.ActivityBaseESService;
import es.redmic.es.administrative.service.ActivityESService;
import es.redmic.models.es.administrative.dto.ActivityDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.JSONCollectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.MgetDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.common.utils.HierarchicalUtils;

@RestController
@RequestMapping(value = "${controller.mapping.ACTIVITY}")
public class ActivityController
		extends RWController<Activity, es.redmic.models.es.administrative.model.Activity, ActivityDTO, DataQueryDTO> {

	@Autowired
	ActivityBaseESService activityBaseESService;

	@Autowired
	public ActivityController(ActivityService service, ActivityESService serviceES) {
		super(service, serviceES);
	}

	@PostMapping(value = "${contoller.mapping.ANCESTORS}/_search")
	@ResponseBody
	public SuperDTO _getAncestors(@PathVariable("path") String path, HttpServletResponse response,
			@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);

		String[] ancestorIds = HierarchicalUtils.getAncestorsIds(path);
		MgetDTO mgetDto = new MgetDTO(Arrays.asList(ancestorIds));

		if (queryDTO.getReturnFields() != null && !queryDTO.getReturnFields().isEmpty()) {
			mgetDto.setFields(queryDTO.getReturnFields());

			if (!mgetDto.getFields().contains("path")) {
				mgetDto.getFields().add("path");
			}
		}

		JSONCollectionDTO result = activityBaseESService.mget(mgetDto);
		return new ElasticSearchDTO(result, result.getTotal());
	}

	@GetMapping(value = "/resources")
	@ResponseBody
	public SuperDTO _search(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = activityBaseESService.createSimpleQueryDTOFromTextQueryParams(fields, text, from, size);

		DataQueryDTO dataQueryDTO = convertToDataQuery((DataQueryDTO) queryDTO);
		processQuery(dataQueryDTO);
		JSONCollectionDTO result = ESService.find(dataQueryDTO);
		return new ElasticSearchDTO(result, result.getTotal());
	}
}
