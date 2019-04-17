package es.redmic.api.maintenance.statistics.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.es.maintenance.statistics.service.StatisticsESService;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.maintenance.statistics.dto.AdministrativeStatisticsDTO;

@RestController
@RequestMapping(value = "${controller.mapping.STATISTICS}")
public class StatisticsESController {

	@Autowired
	StatisticsESService service;

	@RequestMapping(value = "/administrative", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO projectsStatistics() {

		AdministrativeStatisticsDTO response = service.administrativeStatistics();

		return new BodyItemDTO<AdministrativeStatisticsDTO>(response);
	}
}
