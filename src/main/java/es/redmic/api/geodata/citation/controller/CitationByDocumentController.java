package es.redmic.api.geodata.citation.controller;

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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.es.geodata.citation.service.CitationESService;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.citation.dto.CitationDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;

@RestController
@RequestMapping(value = "${controller.mapping.CITATIONS_BY_DOCUMENTS}")
public class CitationByDocumentController extends RBaseController<GeoPointData, CitationDTO, GeoDataQueryDTO> {

	private CitationESService serviceES;

	@Autowired
	public CitationByDocumentController(CitationESService serviceES) {
		super(serviceES);
		this.serviceES = serviceES;
	}

	@PostMapping(value = "/_search")
	@ResponseBody
	public SuperDTO getCitations(@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult, @PathVariable("documentId") String documentId) {

		processQuery(queryDTO, bindingResult);

		return new ElasticSearchDTO(serviceES.findByDocument(queryDTO, documentId));
	}
}
