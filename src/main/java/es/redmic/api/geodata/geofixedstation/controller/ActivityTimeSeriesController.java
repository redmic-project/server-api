package es.redmic.api.geodata.geofixedstation.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RController;
import es.redmic.es.administrative.service.ActivityESService;
import es.redmic.models.es.administrative.dto.ActivityDTO;
import es.redmic.models.es.administrative.model.Activity;
import es.redmic.models.es.common.query.dto.DataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ACTIVITY_TIMESERIES}")
public class ActivityTimeSeriesController extends RController<Activity, ActivityDTO, DataQueryDTO> {
	
	@Autowired
	public ActivityTimeSeriesController(ActivityESService service) {
		super(service);
	}
	
	@PostConstruct
	private void postConstruct() {
		setFixedQuery("activityType.id", "6,31");
	}
}
