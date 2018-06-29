package es.redmic.api.geodata.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.geodata.common.domain.model.Confidence;
import es.redmic.db.geodata.common.domain.service.ConfidenceService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.ConfidenceESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.common.domain.dto.ConfidenceDTO;

@RestController
@RequestMapping(value = "${controller.mapping.CONFIDENCE}")
public class ConfidenceController extends RWController<Confidence, DomainES, ConfidenceDTO, SimpleQueryDTO> {

	@Autowired
	public ConfidenceController(ConfidenceService service, ConfidenceESService serviceES) {
		super(service, serviceES);
	}
}