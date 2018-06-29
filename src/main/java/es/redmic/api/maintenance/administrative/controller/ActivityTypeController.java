package es.redmic.api.maintenance.administrative.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.maintenance.administrative.model.ActivityType;
import es.redmic.db.maintenance.administrative.service.ActivityTypeService;
import es.redmic.es.maintenance.domain.administrative.service.ActivityTypeESService;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.ActivityTypeDTO;

@RestController
@RequestMapping("${controller.mapping.ACTIVITY_TYPE}")
public class ActivityTypeController extends
		RWController<ActivityType, es.redmic.models.es.maintenance.administrative.model.ActivityType, ActivityTypeDTO, MetadataQueryDTO> {

	ActivityTypeESService serviceES;

	@Autowired
	public ActivityTypeController(ActivityTypeService service, ActivityTypeESService serviceES) {
		super(service, serviceES);

		this.serviceES = serviceES;
	}

	@RequestMapping(value = "/{id}/activitycategories", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _getActivityCategories(@PathVariable("id") Long id) {

		List<String> response = serviceES.getActivityCategoriesByActivityType(id);
		return new ElasticSearchDTO(response, response == null ? 0 : 1);
	}

}
