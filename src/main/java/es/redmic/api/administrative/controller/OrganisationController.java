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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.model.Organisation;
import es.redmic.db.administrative.service.OrganisationService;
import es.redmic.es.administrative.service.OrganisationESService;
import es.redmic.models.es.administrative.dto.OrganisationDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ORGANISATION}")
public class OrganisationController extends
		RWController<Organisation, es.redmic.models.es.administrative.model.Organisation, OrganisationDTO, MetadataQueryDTO> {

	private OrganisationESService serviceES;

	@Autowired
	public OrganisationController(OrganisationService service, OrganisationESService serviceES) {
		super(service, serviceES);
		this.serviceES = serviceES;
	}

	@GetMapping(value = "${contoller.mapping.FILTERED_ACTIVITIES}")
	@ResponseBody
	public SuperDTO getActivities(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size, @PathVariable("id") Long id) {

		MetadataQueryDTO queryDTO = objectMapper.convertValue(ESService.createSimpleQueryDTOFromTextQueryParams(fields, text,
				from, size), MetadataQueryDTO.class);

		processQuery(queryDTO);

		return new ElasticSearchDTO(serviceES.getActivities(convertToDataQuery(queryDTO), id));
	}
}
