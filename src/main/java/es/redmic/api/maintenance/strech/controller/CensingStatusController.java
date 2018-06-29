package es.redmic.api.maintenance.strech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.strech.model.CensingStatus;
import es.redmic.db.maintenance.strech.service.CensingStatusService;
import es.redmic.es.maintenance.strech.service.CensingStatusESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.strech.dto.CensingStatusDTO;

@RestController
@RequestMapping(value = "${controller.mapping.CENSING_STATUS}")
public class CensingStatusController extends RWDomainController<CensingStatus, DomainES, CensingStatusDTO, SimpleQueryDTO> {

	@Autowired
	public CensingStatusController(CensingStatusService service, CensingStatusESService serviceES) {
		super(service, serviceES);
	}
}