package es.redmic.api.presence.biological.citation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.presence.biological.common.GeoBiologicalPresenceController;
import es.redmic.es.common.queryFactory.geodata.CitationQueryUtils;
import es.redmic.es.geodata.citation.service.CitationESService;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.citation.dto.CitationDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;

@RestController
@RequestMapping(value = "${controller.mapping.CITATIONS}")
public class CitationPresenceController extends GeoBiologicalPresenceController<GeoPointData, CitationDTO, DataQueryDTO> {

	@Autowired
	public CitationPresenceController(CitationESService service) {
		super(service);
	}
	
	@PostConstruct
	private void postConstruct() {	
		setFieldsExcludedOnQuery(CitationQueryUtils.getFieldsExcludedOnQuery());
	}
}