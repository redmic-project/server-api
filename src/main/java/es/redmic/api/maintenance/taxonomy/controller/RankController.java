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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.Rank;
import es.redmic.db.maintenance.taxonomy.service.RankService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.TaxonRankESService;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.JSONCollectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.RankDTO;

@RestController
@RequestMapping(value = "${controller.mapping.RANK}")
public class RankController extends RWDomainController<Rank, DomainES, RankDTO, SimpleQueryDTO> {

	TaxonRankESService serviceES;

	@Autowired
	public RankController(RankService service, TaxonRankESService serviceES) {
		super(service, serviceES);
		this.serviceES = serviceES;
	}

	@Override
	@GetMapping(value = "${controller.mapping.SPECIES_RANK}")
	@ResponseBody
	public SuperDTO _search(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size,
			@RequestParam(required = false, value = "fields") String[] returnFields) {

		SimpleQueryDTO queryDTO = ESService.createSimpleQueryDTOFromTextQueryParams(fields, text, from, size);
		queryDTO.addTerm("id", "10");
		processQuery(queryDTO);
		JSONCollectionDTO result = ESService.find(convertToDataQuery(queryDTO));
		return new ElasticSearchDTO(result, result.getTotal());
	}

}
