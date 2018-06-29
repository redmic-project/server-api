package es.redmic.api.maintenance.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.administrative.model.ActivityField;
import es.redmic.db.maintenance.administrative.service.ActivityFieldService;
import es.redmic.es.maintenance.domain.administrative.service.ActivityFieldESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.ActivityFieldDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ACTIVITY_FIELD}")
public class ActivityFieldController extends RWDomainController<ActivityField, DomainES, ActivityFieldDTO, SimpleQueryDTO> {

	@Autowired
	public ActivityFieldController(ActivityFieldService service, ActivityFieldESService serviceES) {
		super(service, serviceES);
	}
}
