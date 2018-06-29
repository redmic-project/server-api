package es.redmic.api.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.model.Project;
import es.redmic.db.administrative.service.ProjectService;
import es.redmic.es.administrative.service.ProjectESService;
import es.redmic.models.es.administrative.dto.ProjectDTO;
import es.redmic.models.es.common.query.dto.DataAccessibilityQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.PROJECT}")
public class ProjectController extends
		RWController<Project, es.redmic.models.es.administrative.model.Project, ProjectDTO, DataAccessibilityQueryDTO> {

	@Autowired
	public ProjectController(ProjectService service, ProjectESService serviceES) {
		super(service, serviceES);
	}
}