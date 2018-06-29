package es.redmic.api.geodata.tracking.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RController;
import es.redmic.es.administrative.service.ActivityESService;
import es.redmic.es.common.queryFactory.geodata.TrackingQueryUtils;
import es.redmic.models.es.administrative.dto.ActivityDTO;
import es.redmic.models.es.administrative.model.Activity;
import es.redmic.models.es.common.query.dto.DataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ACTIVITY_TRACKING}")
public class ActivityTrackingController extends RController<Activity, ActivityDTO, DataQueryDTO> {
	
	@Autowired
	public ActivityTrackingController(ActivityESService service) {
		super(service);
	}
	
	@PostConstruct
	private void postConstruct() {
		
		setFixedQuery(TrackingQueryUtils.getActivityCategoryTermQuery());
	}
}
