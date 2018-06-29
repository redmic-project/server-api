package es.redmic.api.geodata.tracking.platform.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.geodata.common.controller.RWGeoDataController;
import es.redmic.db.geodata.tracking.platform.model.PlatformTracking;
import es.redmic.db.geodata.tracking.platform.service.PlatformTrackingService;
import es.redmic.es.common.queryFactory.geodata.TrackingQueryUtils;
import es.redmic.es.geodata.tracking.platform.service.PlatformTrackingESService;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.tracking.platform.dto.PlatformTrackingDTO;

@RestController
@RequestMapping(value = "${controller.mapping.PLATFORMTRACKING_BY_ACTIVITY}")
public class RWPlatformTrackingController extends RWGeoDataController<PlatformTracking, GeoPointData, PlatformTrackingDTO, DataQueryDTO> {
	
	@Autowired
	public RWPlatformTrackingController(PlatformTrackingService service, PlatformTrackingESService serviceES) {
		super(service, serviceES);
	}
	
	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(TrackingQueryUtils.getFieldsExcludedOnQuery());
	}
}