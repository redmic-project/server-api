package es.redmic.api.maintenance.line.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.maintenance.line.model.LineType;
import es.redmic.db.maintenance.line.service.LineTypeService;
import es.redmic.es.maintenance.line.service.LineTypeESService;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.line.dto.LineTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.LINE_TYPE}")
public class LineTypeController extends
		RWController<LineType, es.redmic.models.es.maintenance.line.model.LineType, LineTypeDTO, MetadataQueryDTO> {

	@Autowired
	public LineTypeController(LineTypeService service, LineTypeESService serviceES) {
		super(service, serviceES);
	}
}
