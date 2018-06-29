package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.Status;
import es.redmic.db.maintenance.taxonomy.service.StatusService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.StatusESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.StatusDTO;

@RestController
@RequestMapping(value = "${controller.mapping.STATUS}")
public class StatusController extends RWDomainController<Status, DomainES, StatusDTO, SimpleQueryDTO> {

	@Autowired
	public StatusController(StatusService service, StatusESService serviceES) {
		super(service, serviceES);
	}
}