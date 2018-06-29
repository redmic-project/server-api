package es.redmic.api.maintenance.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.administrative.model.OrganisationRole;
import es.redmic.db.maintenance.administrative.service.OrganisationRoleService;
import es.redmic.es.maintenance.domain.administrative.service.OrganisationRoleESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.OrganisationRoleDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ORGANISATION_ROLES}")
public class OrganisationRoleController extends RWDomainController<OrganisationRole, DomainES, OrganisationRoleDTO, SimpleQueryDTO> {

	@Autowired
	public OrganisationRoleController(OrganisationRoleService service, OrganisationRoleESService serviceES) {
		super(service, serviceES);
	}
}
