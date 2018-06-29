package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.SpainProtection;
import es.redmic.db.maintenance.taxonomy.service.SpainProtectionService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.SpainProtectionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.SpainProtectionDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SPAIN_PROTECTION}")
public class SpainProtectionController extends RWDomainController<SpainProtection, DomainES, SpainProtectionDTO, SimpleQueryDTO> {

	@Autowired
	public SpainProtectionController(SpainProtectionService service, SpainProtectionESService serviceES) {
		super(service, serviceES);
	}
}
