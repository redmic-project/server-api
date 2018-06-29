package es.redmic.api.presence.biological.animalTracking;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.presence.biological.common.GeoBiologicalPresenceController;
import es.redmic.es.common.queryFactory.geodata.TrackingQueryUtils;
import es.redmic.es.geodata.tracking.animal.service.AnimalTrackingESService;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.tracking.animal.dto.AnimalTrackingDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ANIMALTRACKING}")
public class AnimalTrackingPresenceController
		extends GeoBiologicalPresenceController<GeoPointData, AnimalTrackingDTO, DataQueryDTO> {

	@Autowired
	public AnimalTrackingPresenceController(AnimalTrackingESService service) {
		super(service);
	}
	
	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(TrackingQueryUtils.getFieldsExcludedOnQuery());
	}
}
