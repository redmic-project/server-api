package es.redmic.api.maintenance.point.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.maintenance.point.model.InfrastructureType;
import es.redmic.db.maintenance.point.service.InfrastructureTypeService;
import es.redmic.es.maintenance.point.service.InfrastructureTypeESService;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.point.dto.InfrastructureTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.INFRASTRUCTURE_TYPE}")
public class InfrastructureTypeController extends RWController<InfrastructureType, es.redmic.models.es.maintenance.point.model.InfrastructureType, InfrastructureTypeDTO, SimpleQueryDTO> {

	@Autowired
	public InfrastructureTypeController(InfrastructureTypeService service, InfrastructureTypeESService serviceES) {
		super(service, serviceES);
	}
}