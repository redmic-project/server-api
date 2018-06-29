package es.redmic.api.maintenance.parameter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.parameter.model.UnitType;
import es.redmic.db.maintenance.parameter.service.UnitTypeService;
import es.redmic.es.maintenance.parameter.service.UnitTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.parameter.dto.UnitTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.UNIT_TYPE}")
public class UnitTypeController extends RWDomainController<UnitType, DomainES, UnitTypeDTO, SimpleQueryDTO> {

	@Autowired
	public UnitTypeController(UnitTypeService service, UnitTypeESService serviceES) {
		super(service, serviceES);
	}
}