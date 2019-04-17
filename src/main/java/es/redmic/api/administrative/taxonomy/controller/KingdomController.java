package es.redmic.api.administrative.taxonomy.controller;

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
import es.redmic.db.administrative.taxonomy.model.Kingdom;
import es.redmic.db.administrative.taxonomy.service.KingdomService;
import es.redmic.es.administrative.taxonomy.service.KingdomESService;
import es.redmic.models.es.administrative.taxonomy.dto.KingdomDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.KINGDOM}")
public class KingdomController extends RWController<Kingdom, Taxon, KingdomDTO, MetadataQueryDTO> {

	@Autowired
	public KingdomController(KingdomService service, KingdomESService serviceES) {
		super(service, serviceES);
	}
}
