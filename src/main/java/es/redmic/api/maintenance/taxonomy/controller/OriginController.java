package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.Origin;
import es.redmic.db.maintenance.taxonomy.service.OriginService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.OriginESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.OriginDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ORIGIN}")
public class OriginController extends RWDomainController<Origin, DomainES, OriginDTO, SimpleQueryDTO> {

	@Autowired
	public OriginController(OriginService service, OriginESService serviceES) {
		super(service, serviceES);
	}
}
