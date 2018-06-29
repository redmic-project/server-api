package es.redmic.api.maintenance.parameter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.parameter.model.ParameterType;
import es.redmic.db.maintenance.parameter.service.ParameterTypeService;
import es.redmic.es.maintenance.parameter.service.ParameterTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.parameter.dto.ParameterTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.PARAMETER_TYPE}")
public class ParameterTypeController extends RWDomainController<ParameterType, DomainES, ParameterTypeDTO, SimpleQueryDTO> {

	@Autowired
	public ParameterTypeController(ParameterTypeService service, ParameterTypeESService serviceES) {
		super(service, serviceES);
	}
}
