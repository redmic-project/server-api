package es.redmic.api.maintenance.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.administrative.model.ContactRole;
import es.redmic.db.maintenance.administrative.service.ContactRoleService;
import es.redmic.es.maintenance.domain.administrative.service.ContactRoleESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.ContactRoleDTO;

@RestController
@RequestMapping(value = "${controller.mapping.CONTACT_ROLE}")
public class ContactRoleController extends RWDomainController<ContactRole, DomainES, ContactRoleDTO, SimpleQueryDTO> {

	@Autowired
	public ContactRoleController(ContactRoleService service, ContactRoleESService serviceES) {
		super(service, serviceES);
	}
}