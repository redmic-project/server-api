package es.redmic.api.maintenance.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.administrative.model.PlatformType;
import es.redmic.db.maintenance.administrative.service.PlatformTypeService;
import es.redmic.es.maintenance.domain.administrative.service.PlatformTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.PlatformTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.PLATFORM_TYPE}")
public class PlatformTypeController extends RWDomainController<PlatformType, DomainES, PlatformTypeDTO, SimpleQueryDTO> {

	@Autowired
	public PlatformTypeController(PlatformTypeService service, PlatformTypeESService serviceES) {
		super(service, serviceES);
	}
}
