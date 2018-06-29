package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.Permanence;
import es.redmic.db.maintenance.taxonomy.service.PermanenceService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.PermanenceESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.PermanenceDTO;

@RestController
@RequestMapping(value = "${controller.mapping.PERMANENCE}")
public class PermanenceController extends RWDomainController<Permanence, DomainES, PermanenceDTO, SimpleQueryDTO> {

	@Autowired
	public PermanenceController(PermanenceService service, PermanenceESService serviceES) {
		super(service, serviceES);
	}
}