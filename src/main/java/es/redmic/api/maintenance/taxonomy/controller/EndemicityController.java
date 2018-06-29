package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.Endemicity;
import es.redmic.db.maintenance.taxonomy.service.EndemicityService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.EndemicityESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.EndemicityDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ENDEMICITY}")
public class EndemicityController extends RWDomainController<Endemicity, DomainES, EndemicityDTO, SimpleQueryDTO> {

	@Autowired
	public EndemicityController(EndemicityService service, EndemicityESService serviceES) {
		super(service, serviceES);
	}
}