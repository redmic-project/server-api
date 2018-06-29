package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.Interest;
import es.redmic.db.maintenance.taxonomy.service.InterestService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.InterestESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.InterestDTO;

@RestController
@RequestMapping(value = "${controller.mapping.INTEREST}")
public class InterestController extends RWDomainController<Interest, DomainES, InterestDTO, SimpleQueryDTO> {

	@Autowired
	public InterestController(InterestService service, InterestESService serviceES) {
		super(service, serviceES);
	}
}
