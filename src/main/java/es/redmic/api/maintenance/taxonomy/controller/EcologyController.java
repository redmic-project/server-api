package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.Ecology;
import es.redmic.db.maintenance.taxonomy.service.EcologyService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.EcologyESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.EcologyDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ECOLOGY}")
public class EcologyController extends RWDomainController<Ecology, DomainES, EcologyDTO, SimpleQueryDTO> {

	@Autowired
	public EcologyController(EcologyService service, EcologyESService serviceES) {
		super(service, serviceES);
	}
}