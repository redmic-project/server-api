package es.redmic.api.maintenance.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.administrative.model.ProjectGroup;
import es.redmic.db.maintenance.administrative.service.ProjectGroupService;
import es.redmic.es.maintenance.domain.administrative.service.ProjectGroupESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.ProjectGroupDTO;

@RestController
@RequestMapping(value = "${controller.mapping.PROJECT_GROUPS}")
public class ProjectGroupController extends RWDomainController<ProjectGroup, DomainES, ProjectGroupDTO, SimpleQueryDTO> {

	@Autowired
	public ProjectGroupController(ProjectGroupService service, ProjectGroupESService serviceES) {
		super(service, serviceES);
	}
}
