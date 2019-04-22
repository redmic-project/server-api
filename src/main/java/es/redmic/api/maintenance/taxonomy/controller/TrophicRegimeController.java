package es.redmic.api.maintenance.taxonomy.controller;

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
import es.redmic.db.maintenance.taxonomy.model.TrophicRegime;
import es.redmic.db.maintenance.taxonomy.service.TrophicRegimeService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.TrophicRegimeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.TrophicRegimeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.TROPHIC_REGIME}")
public class TrophicRegimeController extends RWDomainController<TrophicRegime, DomainES, TrophicRegimeDTO, SimpleQueryDTO> {

	@Autowired
	public TrophicRegimeController(TrophicRegimeService service, TrophicRegimeESService serviceES) {
		super(service, serviceES);
	}
}
