package es.redmic.api.maintenance.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.administrative.model.OrganisationType;
import es.redmic.db.maintenance.administrative.service.OrganisationTypeService;
import es.redmic.es.maintenance.domain.administrative.service.OrganisationTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.OrganisationTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ORGANISATION_TYPES}")
public class OrganisationTypeController extends RWDomainController<OrganisationType, DomainES, OrganisationTypeDTO, SimpleQueryDTO> {

	@Autowired
	public OrganisationTypeController(OrganisationTypeService service, OrganisationTypeESService serviceES) {
		super(service, serviceES);
	}
}
