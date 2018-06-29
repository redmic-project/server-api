package es.redmic.api.maintenance.areas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.areas.model.AreaType;
import es.redmic.db.maintenance.areas.service.AreaTypeService;
import es.redmic.es.maintenance.area.service.AreaTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.areas.dto.AreaTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.AREA_TYPE}")
public class AreaTypeController extends RWDomainController<AreaType, DomainES, AreaTypeDTO, SimpleQueryDTO> {

	@Autowired
	public AreaTypeController(AreaTypeService service, AreaTypeESService serviceES) {
		super(service, serviceES);
	}
}
