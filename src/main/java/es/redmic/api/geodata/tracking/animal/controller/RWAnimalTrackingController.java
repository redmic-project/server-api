package es.redmic.api.geodata.tracking.animal.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.geodata.common.controller.RWGeoDataController;
import es.redmic.db.geodata.tracking.animal.model.AnimalTracking;
import es.redmic.db.geodata.tracking.animal.service.AnimalTrackingService;
import es.redmic.es.common.queryFactory.geodata.TrackingQueryUtils;
import es.redmic.es.geodata.tracking.animal.service.AnimalTrackingESService;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.tracking.animal.dto.AnimalTrackingDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ANIMALTRACKING_BY_ACTIVITY}")
public class RWAnimalTrackingController extends RWGeoDataController<AnimalTracking, GeoPointData, AnimalTrackingDTO, DataQueryDTO> {
	
	@Autowired
	public RWAnimalTrackingController(AnimalTrackingService service, AnimalTrackingESService serviceES) {
		super(service, serviceES);
	}
	
	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(TrackingQueryUtils.getFieldsExcludedOnQuery());
	}
}