package es.redmic.api.maintenance.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.db.maintenance.common.model.Accessibility;
import es.redmic.db.maintenance.common.service.AccessibilityService;
import es.redmic.es.maintenance.domain.administrative.service.AccessibilityESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.common.dto.AccessibilityDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ACCESSIBILITY}")
public class AccessibilityController extends RWDomainController<Accessibility, DomainES, AccessibilityDTO, SimpleQueryDTO> {

	@Autowired
	public AccessibilityController(AccessibilityService service, AccessibilityESService serviceES) {
		super(service, serviceES);
	}
}
