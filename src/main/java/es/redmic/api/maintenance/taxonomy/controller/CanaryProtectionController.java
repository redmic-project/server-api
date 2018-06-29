package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.CanaryProtection;
import es.redmic.db.maintenance.taxonomy.service.CanaryProtectionService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.CanaryProtectionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.CanaryProtectionDTO;

@RestController
@RequestMapping(value = "${controller.mapping.CANARY_PROTECTION}")
public class CanaryProtectionController extends RWDomainController<CanaryProtection, DomainES, CanaryProtectionDTO, SimpleQueryDTO> {

	@Autowired
	public CanaryProtectionController(CanaryProtectionService service, CanaryProtectionESService serviceES) {
		super(service, serviceES);
	}
}
