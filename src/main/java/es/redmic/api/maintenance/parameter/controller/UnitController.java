package es.redmic.api.maintenance.parameter.controller;

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

import es.redmic.api.common.controller.RWController;
import es.redmic.db.maintenance.parameter.model.Unit;
import es.redmic.db.maintenance.parameter.service.UnitService;
import es.redmic.es.maintenance.parameter.service.UnitESService;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.parameter.dto.UnitDTO;

@RestController
@RequestMapping(value = "${controller.mapping.UNIT}")
public class UnitController
		extends RWController<Unit, es.redmic.models.es.maintenance.parameter.model.Unit, UnitDTO, MetadataQueryDTO> {

	@Autowired
	public UnitController(UnitService service, UnitESService serviceES) {
		super(service, serviceES);
	}
}
