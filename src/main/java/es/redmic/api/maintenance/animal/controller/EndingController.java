package es.redmic.api.maintenance.animal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.animal.model.Ending;
import es.redmic.db.maintenance.animal.service.EndingService;
import es.redmic.es.maintenance.animal.service.EndingESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.animal.dto.EndingDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ENDING}")
public class EndingController extends RWDomainController<Ending, DomainES, EndingDTO, SimpleQueryDTO> {

	@Autowired
	public EndingController(EndingService service, EndingESService serviceES) {
		super(service, serviceES);
	}
}