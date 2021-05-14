package es.redmic.api.tools.distributions.species.controller;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.SelectionWorkController;
import es.redmic.es.administrative.taxonomy.service.SpeciesESService;
import es.redmic.es.common.service.SelectionService;
import es.redmic.es.common.service.SelectionWorkService;
import es.redmic.es.tools.distributions.species.service.TaxonDist1000MService;
import es.redmic.es.tools.distributions.species.service.TaxonDist100MService;
import es.redmic.es.tools.distributions.species.service.TaxonDist5000MService;
import es.redmic.es.tools.distributions.species.service.TaxonDist500MService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.administrative.taxonomy.dto.SpeciesDTO;
import es.redmic.models.es.administrative.taxonomy.model.Species;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.BodyListDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SelectionWorkDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.common.dto.GeoJSONFeatureCollectionDTO;
import es.redmic.models.es.tools.distribution.dto.TaxonDistributionRegistersDTO;

@RestController
@RequestMapping(value = "${controller.mapping.DISTRIBUTIONS}")
public class SpeciesDistributionController extends SelectionWorkController<Species, SpeciesDTO, GeoDataQueryDTO> {

	@Autowired
	SelectionWorkService selectionWorkService;

	@Autowired
	SelectionService selectionService;

	@Autowired
	private TaxonDist100MService dist100MService;

	@Autowired
	private TaxonDist500MService dist500MService;

	@Autowired
	private TaxonDist1000MService dist1000MService;

	@Autowired
	private TaxonDist5000MService dist5000MService;

	SpeciesESService speciesESService;

	@Autowired
	public SpeciesDistributionController(SpeciesESService speciesESService) {
		super(speciesESService);
		this.speciesESService = speciesESService;
	}

	@PostMapping(value = "${controller.mapping.GRID100}/_search")
	@ResponseBody
	public SuperDTO findAll100M(@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult,
			HttpServletResponse response) {
		processQuery(queryDTO, bindingResult);
		return new BodyItemDTO<GeoJSONFeatureCollectionDTO>(dist100MService.findAll(queryDTO));
	}

	@PostMapping(value = "${controller.mapping.GRID100_BY_ID}/{id}/_search")
	@ResponseBody
	public SuperDTO find100MById(@PathVariable("id") String id, @Valid @RequestBody GeoDataQueryDTO queryDTO,
			BindingResult bindingResult, HttpServletResponse response) {
		processQuery(queryDTO, bindingResult);
		return new BodyListDTO<TaxonDistributionRegistersDTO>(dist100MService.findByGridIdAndTaxons(id, queryDTO));
	}

	@PostMapping(value = "${controller.mapping.GRID500}/_search")
	@ResponseBody
	public SuperDTO findAll500M(@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult,
			HttpServletResponse response) {
		processQuery(queryDTO, bindingResult);
		return new BodyItemDTO<GeoJSONFeatureCollectionDTO>(dist500MService.findAll(queryDTO));
	}

	@PostMapping(value = "${controller.mapping.GRID500_BY_IDE}/{id}/_search")
	@ResponseBody
	public SuperDTO find500MById(@PathVariable("id") String id, @Valid @RequestBody GeoDataQueryDTO queryDTO,
			BindingResult bindingResult, HttpServletResponse response) {
		processQuery(queryDTO, bindingResult);
		return new BodyListDTO<TaxonDistributionRegistersDTO>(dist500MService.findByGridIdAndTaxons(id, queryDTO));
	}

	@PostMapping(value = "${controller.mapping.GRID1000}/_search")
	@ResponseBody
	public SuperDTO findAll1000M(@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult,
			HttpServletResponse response) {
		processQuery(queryDTO, bindingResult);
		return new BodyItemDTO<GeoJSONFeatureCollectionDTO>(dist1000MService.findAll(queryDTO));
	}

	@PostMapping(value = "${controller.mapping.GRID1000_BY_ID}/_search")
	@ResponseBody
	public SuperDTO find1000MById(@PathVariable("id") String id, @Valid @RequestBody GeoDataQueryDTO queryDTO,
			BindingResult bindingResult, HttpServletResponse response) {
		processQuery(queryDTO, bindingResult);
		return new BodyListDTO<TaxonDistributionRegistersDTO>(dist1000MService.findByGridIdAndTaxons(id, queryDTO));
	}

	@PostMapping(value = "${controller.mapping.GRID5000}/_search")
	@ResponseBody
	public SuperDTO findAll5000M(@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult,
			HttpServletResponse response) {
		processQuery(queryDTO, bindingResult);
		return new BodyItemDTO<GeoJSONFeatureCollectionDTO>(dist5000MService.findAll(queryDTO));
	}

	@PostMapping(value = "${controller.mapping.GRID5000_BY_ID}/_search")
	@ResponseBody
	public SuperDTO find5000MById(@PathVariable("id") String id, @Valid @RequestBody GeoDataQueryDTO queryDTO,
			BindingResult bindingResult, HttpServletResponse response) {
		processQuery(queryDTO, bindingResult);
		return new BodyListDTO<TaxonDistributionRegistersDTO>(dist5000MService.findByGridIdAndTaxons(id, queryDTO));
	}

	@GetMapping(value = "${controller.mapping.SELECTION_WORK}/{id}")
	@ResponseBody
	public SuperDTO getSelectionWork(@PathVariable("id") String id) {
		SelectionWorkDTO out = selectionWorkService.get(id);
		if (out != null && out.getIds() != null)
			return new ElasticSearchDTO(out, out.getIds().size());
		else
			return new ElasticSearchDTO(out, 0);
	}

	@PostMapping(value = "${controller.mapping.SELECTION_WORK}")
	@ResponseBody
	public SuperDTO saveSelectionWork(@Valid @RequestBody SelectionWorkDTO dto, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		dto.setIdProperty("path");
		if (dto.getIds() != null && !dto.getIds().isEmpty())
			dto.setIds(speciesESService.getDescendantsIds(dto.getIds()));

		ElasticSearchDTO result = (ElasticSearchDTO) super.saveSelectionWork(dto, bindingResult, request);

		result.setTotal(speciesESService.getCountLeaves(dto.getIds()));
		return result;
	}

	@PutMapping(value = "${controller.mapping.SELECTION_WORK}/{id}")
	@ResponseBody
	public SuperDTO updateSelection(@Valid @RequestBody SelectionWorkDTO dto, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		dto.setIdProperty("path");
		if (dto.getIds() != null && !dto.getIds().isEmpty())
			dto.setIds(speciesESService.getDescendantsIds(dto.getIds()));

		ElasticSearchDTO result = (ElasticSearchDTO) super.updateSelection(dto, bindingResult, request);

		result.setTotal(speciesESService.getCountLeaves(dto.getIds()));
		return result;
	}
}
