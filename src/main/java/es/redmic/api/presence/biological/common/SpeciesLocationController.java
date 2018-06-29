package es.redmic.api.presence.biological.common;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.es.administrative.taxonomy.service.SpeciesESService;
import es.redmic.es.geodata.citation.service.CitationESService;
import es.redmic.es.geodata.tracking.animal.service.AnimalTrackingESService;
import es.redmic.models.es.administrative.taxonomy.dto.SpeciesDTO;
import es.redmic.models.es.administrative.taxonomy.model.Species;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.common.dto.GeoJSONFeatureCollectionDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SPECIES_LOCATION}")
public class SpeciesLocationController extends RBaseController<Species, SpeciesDTO, DataQueryDTO> {

	@Autowired
	CitationESService citationService;

	@Autowired
	AnimalTrackingESService animalTrackingService;
	
	@Autowired
	public SpeciesLocationController(SpeciesESService service) {
		super(service);
	}
	
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO getLocations(@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult,
			@PathVariable("id") String id) {

		queryDTO.addTerm("taxonId", id);

		processQuery(queryDTO, bindingResult);

		GeoJSONFeatureCollectionDTO resp = animalTrackingService.find(queryDTO),
				respCitation = citationService.find(queryDTO),
				result = new GeoJSONFeatureCollectionDTO();

		List<GeoJSONFeatureCollectionDTO> features = new ArrayList<GeoJSONFeatureCollectionDTO>();
		
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
