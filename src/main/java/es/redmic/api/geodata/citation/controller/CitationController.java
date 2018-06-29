package es.redmic.api.geodata.citation.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.geodata.common.controller.RWGeoDataController;
import es.redmic.db.geodata.citation.model.Citation;
import es.redmic.db.geodata.citation.service.CitationService;
import es.redmic.es.common.queryFactory.geodata.CitationQueryUtils;
import es.redmic.es.geodata.citation.service.CitationESService;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.citation.dto.CitationDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;

@RestController
@RequestMapping(value = "${controller.mapping.CITATIONS_BY_ACTIVITY}")
public class CitationController extends RWGeoDataController<Citation, GeoPointData, CitationDTO, DataQueryDTO> {

	@Autowired
	public CitationController(CitationService service, CitationESService serviceES) {
		super(service, serviceES);
	}
	
	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(CitationQueryUtils.getFieldsExcludedOnQuery());
	}
}