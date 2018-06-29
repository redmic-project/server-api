package es.redmic.api.maintenance.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.administrative.model.Scope;
import es.redmic.db.maintenance.administrative.service.ScopeService;
import es.redmic.es.maintenance.domain.administrative.service.ScopeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.ScopeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SCOPES}")
public class ScopeController extends RWDomainController<Scope, DomainES, ScopeDTO, SimpleQueryDTO> {

	@Autowired
	public ScopeController(ScopeService service, ScopeESService serviceES) {
		super(service, serviceES);
	}
}
