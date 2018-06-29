package es.redmic.api.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.model.Program;
import es.redmic.db.administrative.service.ProgramService;
import es.redmic.es.administrative.service.ProgramESService;
import es.redmic.models.es.administrative.dto.ProgramDTO;
import es.redmic.models.es.common.query.dto.DataAccessibilityQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.PROGRAM}")
public class ProgramController extends
		RWController<Program, es.redmic.models.es.administrative.model.Program, ProgramDTO, DataAccessibilityQueryDTO> {

	@Autowired
	public ProgramController(ProgramService service, ProgramESService serviceES) {
		super(service, serviceES);
	}
}