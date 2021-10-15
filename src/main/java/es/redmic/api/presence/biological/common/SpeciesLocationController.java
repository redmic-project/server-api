package es.redmic.api.presence.biological.common;

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

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.es.administrative.taxonomy.service.SpeciesESService;
import es.redmic.es.common.queryFactory.geodata.AnimalTrackingQueryUtils;
import es.redmic.es.geodata.citation.service.CitationESService;
import es.redmic.es.geodata.tracking.animal.service.AnimalTrackingESService;
import es.redmic.models.es.administrative.taxonomy.dto.SpeciesDTO;
import es.redmic.models.es.administrative.taxonomy.model.Species;
import es.redmic.models.es.common.DataPrefixType;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.citation.dto.CitationDTO;
import es.redmic.models.es.geojson.common.dto.GeoJSONFeatureCollectionDTO;
import es.redmic.models.es.geojson.tracking.animal.dto.AnimalTrackingDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SPECIES_LOCATION}")
public class SpeciesLocationController extends RBaseController<Species, SpeciesDTO, GeoDataQueryDTO> {

	@Autowired
	CitationESService citationService;

	@Autowired
	AnimalTrackingESService animalTrackingService;

	@Autowired
	public SpeciesLocationController(SpeciesESService service) {
		super(service);
	}

	@SuppressWarnings({ "unchecked" })
	@PostMapping(value = "/_search")
	@ResponseBody
	public SuperDTO getLocations(@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult,
			@PathVariable("id") String id) {

		queryDTO.addTerm("taxonId", id);

		processQuery(queryDTO, bindingResult);

		queryDTO.setDataType(DataPrefixType.getPrefixTypeFromClass(AnimalTrackingDTO.class));
		GeoJSONFeatureCollectionDTO resp = animalTrackingService.find(queryDTO);

		queryDTO.setDataType(DataPrefixType.getPrefixTypeFromClass(CitationDTO.class));
		GeoJSONFeatureCollectionDTO respCitation = citationService.find(queryDTO);

		GeoJSONFeatureCollectionDTO result = new GeoJSONFeatureCollectionDTO();

		List<GeoJSONFeatureCollectionDTO> features = new ArrayList<>();

		int total = 0;

		if (resp != null) {
			features.addAll(resp.getFeatures());
			total += resp.getTotal();
		}

		if (respCitation != null) {
			features.addAll(respCitation.getFeatures());
			total += respCitation.getTotal();
		}

		result.setTotal(total);
		result.setFeatures(features);

		return new ElasticSearchDTO(result);
	}
}
