package es.redmic.api.maintenance.animal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.animal.model.LifeStage;
import es.redmic.db.maintenance.animal.service.LifeStageService;
import es.redmic.es.maintenance.animal.service.LifeStageESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.animal.dto.LifeStageDTO;

@RestController
@RequestMapping(value = "${controller.mapping.LIFE_STAGE}")
public class LifeStageController extends RWDomainController<LifeStage, DomainES, LifeStageDTO, SimpleQueryDTO> {

	@Autowired
	public LifeStageController(LifeStageService service, LifeStageESService serviceES) {
		super(service, serviceES);
	}
}