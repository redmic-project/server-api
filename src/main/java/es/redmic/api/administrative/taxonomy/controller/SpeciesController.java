package es.redmic.api.administrative.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.AbstractSpecies;
import es.redmic.db.administrative.taxonomy.service.SpeciesService;
import es.redmic.es.administrative.taxonomy.service.SpeciesESService;
import es.redmic.models.es.administrative.taxonomy.dto.SpeciesDTO;
import es.redmic.models.es.administrative.taxonomy.model.Species;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SPECIES}")
public class SpeciesController extends RWController<AbstractSpecies, Species, SpeciesDTO, MetadataQueryDTO> {

	private SpeciesESService serviceES;

	@Autowired
	public SpeciesController(SpeciesService service, SpeciesESService serviceES) {
		super(service, serviceES);
		this.serviceES = serviceES;
	}

	@RequestMapping(value = "${contoller.mapping.FILTERED_ACTIVITIES}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO getActivities(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size, @PathVariable("id") String id) {

		MetadataQueryDTO queryDTO = objectMapper.convertValue(ESService.createSimpleQueryDTOFromTextQueryParams(fields, text, from, size), MetadataQueryDTO.class);
		processQuery(queryDTO);

		return new ElasticSearchDTO(serviceES.getActivities(convertToQuery(queryDTO), id));
	}

	@RequestMapping(value = "${contoller.mapping.FILTERED_DOCUMENTS}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO getDocuments(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size, @PathVariable("id") String id) {

		MetadataQueryDTO queryDTO = objectMapper.convertValue(
				ESService.createSimpleQueryDTOFromTextQueryParams(fields, text, from, size), MetadataQueryDTO.class);
		processQuery(queryDTO);

		return new ElasticSearchDTO(serviceES.getDocuments(convertToQuery(queryDTO), id));
	}
}
