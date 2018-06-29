package es.redmic.api.maintenance.animal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.animal.model.Destiny;
import es.redmic.db.maintenance.animal.service.DestinyService;
import es.redmic.es.maintenance.animal.service.DestinyESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.animal.dto.DestinyDTO;

@RestController
@RequestMapping(value = "${controller.mapping.DESTINY}")
public class DestinyController extends RWDomainController<Destiny, DomainES, DestinyDTO, SimpleQueryDTO> {

	@Autowired
	public DestinyController(DestinyService service, DestinyESService serviceES) {
		super(service, serviceES);
	}
}
