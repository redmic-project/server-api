package es.redmic.api.maintenance.animal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.animal.model.Sex;
import es.redmic.db.maintenance.animal.service.SexService;
import es.redmic.es.maintenance.animal.service.SexESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.animal.dto.SexDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SEX}")
public class SexController extends RWDomainController<Sex, DomainES, SexDTO, SimpleQueryDTO> {

	@Autowired
	public SexController(SexService service, SexESService serviceES) {
		super(service, serviceES);
	}
}
