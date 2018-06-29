package es.redmic.api.geodata.tracking.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.geodata.common.controller.RGeoDataController;
import es.redmic.es.common.queryFactory.geodata.TrackingQueryUtils;
import es.redmic.es.geodata.tracking.common.service.TrackingESService;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.tracking.common.ElementTrackingDTO;

@RestController
@RequestMapping(value = "${controller.mapping.TRACKING_BY_ACTIVITY}")
public class RTrackingController extends RGeoDataController<GeoPointData, ElementTrackingDTO, DataQueryDTO> {

	TrackingESService service;
	
	@Autowired
	public RTrackingController(TrackingESService serviceES) {
		super(serviceES);
		this.service = serviceES;
	}
	
	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(TrackingQueryUtils.getFieldsExcludedOnQuery());
	}
	
	@RequestMapping(value = "${controller.mapping.TRACK_CLUSTER}/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO getCluster(@PathVariable("activityId") String activityId,
			@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);
		
		return new ElasticSearchDTO(service.getTrackingPointsInLineStringCluster(activityId, queryDTO));
	}
		
}