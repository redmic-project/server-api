package es.redmic.api.maintenance.animal.controller;

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
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.animal.model.Ending;
import es.redmic.db.maintenance.animal.service.EndingService;
import es.redmic.es.maintenance.animal.service.EndingESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.animal.dto.EndingDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ENDING}")
public class EndingController extends RWDomainController<Ending, DomainES, EndingDTO, SimpleQueryDTO> {

	@Autowired
	public EndingController(EndingService service, EndingESService serviceES) {
		super(service, serviceES);
	}
}
