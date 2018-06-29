package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.EUProtection;
import es.redmic.db.maintenance.taxonomy.service.EUProtectionService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.EUProtectionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.EUProtectionDTO;

@RestController
@RequestMapping(value = "${controller.mapping.EU_PROTECTION}")
public class EUProtectionController extends RWDomainController<EUProtection, DomainES, EUProtectionDTO, SimpleQueryDTO> {

	@Autowired
	public EUProtectionController(EUProtectionService service, EUProtectionESService serviceES) {
		super(service, serviceES);
	}
}