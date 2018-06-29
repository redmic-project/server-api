package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "${controller.mapping.SPECIES_RANK}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _search(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = ESService.createSimpleQueryDTOFromTextQueryParams(fields, text, from, size);
		queryDTO.addTerm("id", "10");
		processQuery(queryDTO);
		JSONCollectionDTO result = ESService.find(convertToQuery(queryDTO));
		return new ElasticSearchDTO(result, result.getTotal());
	}

}
