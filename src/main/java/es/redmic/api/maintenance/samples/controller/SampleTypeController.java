package es.redmic.api.maintenance.samples.controller;

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
import es.redmic.db.maintenance.samples.model.SampleType;
import es.redmic.db.maintenance.samples.service.SampleTypeService;
import es.redmic.es.maintenance.samples.service.SampleTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.samples.dto.SampleTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SAMPLE_TYPE}")
public class SampleTypeController extends RWDomainController<SampleType, DomainES, SampleTypeDTO, SimpleQueryDTO> {

	@Autowired
	public SampleTypeController(SampleTypeService service, SampleTypeESService serviceES) {
		super(service, serviceES);
	}
}
