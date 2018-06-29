package es.redmic.api.maintenance.strech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.strech.model.SeaCondition;
import es.redmic.db.maintenance.strech.service.SeaConditionService;
import es.redmic.es.maintenance.strech.service.SeaConditionESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.strech.dto.SeaConditionDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SEA_CONDITION}")
public class SeaConditionController extends RWDomainController<SeaCondition, DomainES, SeaConditionDTO, SimpleQueryDTO> {

	@Autowired
	public SeaConditionController(SeaConditionService service, SeaConditionESService serviceES) {
		super(service, serviceES);
	}
}