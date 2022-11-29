package es.redmic.api.administrative.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import es.redmic.db.administrative.model.Document;
import es.redmic.db.administrative.service.DocumentService;
import es.redmic.es.administrative.service.DocumentESService;
import es.redmic.exception.common.NotFoundException;
import es.redmic.exception.data.ItemNotFoundException;
import es.redmic.models.es.administrative.dto.DocumentDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.JSONCollectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.DOCUMENT}")
public class DocumentController extends
		RWController<Document, es.redmic.models.es.administrative.model.Document, DocumentDTO, MetadataQueryDTO> {

	private DocumentESService serviceES;

	@Autowired
	public DocumentController(DocumentService service, DocumentESService serviceES) {
		super(service, serviceES);
		this.serviceES = serviceES;
	}

	@Override
	protected Map<String, Object> getFixedQuery() {

		Map<String, Object> fixedQuery = super.getFixedQuery();

		List<String> roles = userService.getUserRole();

		if (!roles.contains("ROLE_ADMINISTRATOR")) {

			fixedQuery.put("only_enable", true);
			return fixedQuery;
		}
		fixedQuery.remove("only_enable");
		return fixedQuery;
	}

	@GetMapping(value = "${contoller.mapping.FILTERED_ACTIVITIES}")
	@ResponseBody
	public SuperDTO getActivities(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size, @PathVariable("id") Long id) {

		MetadataQueryDTO queryDTO = objectMapper.convertValue(
				ESService.createSimpleQueryDTOFromTextQueryParams(fields, text, from, size), MetadataQueryDTO.class);
		processQuery(queryDTO);

		return new ElasticSearchDTO(serviceES.getActivities(convertToDataQuery(queryDTO), id));
	}

	@Override
	protected JSONCollectionDTO postFilter(JSONCollectionDTO jsonCollectionDTO) {

		List<String> roles = userService.getUserRole();

		if (jsonCollectionDTO.getTotal() > 0) {
			List<DocumentDTO> result = jsonCollectionDTO.getData();
			result.removeIf(tdto -> !roles.contains("ROLE_ADMINISTRATOR") && Boolean.FALSE.equals(tdto.getEnabled()));
			jsonCollectionDTO.setData(result);
		}
		return jsonCollectionDTO;
	}

	@Override
	protected DocumentDTO postFilter(DocumentDTO tdto) throws NotFoundException {

		List<String> roles = userService.getUserRole();

		if (!roles.contains("ROLE_ADMINISTRATOR") && Boolean.FALSE.equals(tdto.getEnabled())) {
			throw new  ItemNotFoundException("id", tdto.getId().toString());
		}

		return tdto;
	}
}
