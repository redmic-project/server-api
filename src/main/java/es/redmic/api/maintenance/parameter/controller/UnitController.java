package es.redmic.api.maintenance.parameter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.maintenance.parameter.model.Unit;
import es.redmic.db.maintenance.parameter.service.UnitService;
import es.redmic.es.maintenance.parameter.service.UnitESService;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.parameter.dto.UnitDTO;

@RestController
@RequestMapping(value = "${controller.mapping.UNIT}")
public class UnitController
		extends RWController<Unit, es.redmic.models.es.maintenance.parameter.model.Unit, UnitDTO, MetadataQueryDTO> {

	@Autowired
	public UnitController(UnitService service, UnitESService serviceES) {
		super(service, serviceES);
	}
}