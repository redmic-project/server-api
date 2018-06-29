package es.redmic.api.maintenance.areas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.areas.model.ThematicType;
import es.redmic.db.maintenance.areas.service.ThematicTypeService;
import es.redmic.es.maintenance.area.service.ThematicTypeESService;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.areas.dto.ThematicTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.THEMATIC_TYPE}")
public class ThematicTypeController extends
		RWDomainController<ThematicType, es.redmic.models.es.maintenance.areas.model.ThematicType, ThematicTypeDTO, SimpleQueryDTO> {

	@Autowired
	public ThematicTypeController(ThematicTypeService service, ThematicTypeESService serviceES) {
		super(service, serviceES);
	}
}