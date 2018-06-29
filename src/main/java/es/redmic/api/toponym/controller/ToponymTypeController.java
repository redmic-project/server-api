package es.redmic.api.toponym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.toponym.model.ToponymType;
import es.redmic.db.maintenance.toponym.service.ToponymTypeService;
import es.redmic.es.maintenance.toponym.service.ToponymTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.toponym.dto.ToponymTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.TOPONYM_TYPE}")
public class ToponymTypeController extends RWDomainController<ToponymType, DomainES, ToponymTypeDTO, SimpleQueryDTO> {

	@Autowired
	public ToponymTypeController(ToponymTypeService service, ToponymTypeESService serviceES) {
		super(service, serviceES);
	}
}
