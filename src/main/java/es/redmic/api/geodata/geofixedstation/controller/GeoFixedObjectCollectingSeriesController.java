package es.redmic.api.geodata.geofixedstation.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.geodata.common.controller.RWGeoDataController;
import es.redmic.db.geodata.properties.fixedsurvey.model.FixedSurvey;
import es.redmic.db.geodata.properties.fixedsurvey.service.FixedSurveyObjectCollectingService;
import es.redmic.es.common.queryFactory.geodata.GeoFixedSeriesQueryUtils;
import es.redmic.es.geodata.geofixedstation.service.GeoFixedObjectCollectingSeriesESService;
import es.redmic.models.es.common.DataPrefixType;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoLineStringData;
import es.redmic.models.es.geojson.geofixedstation.dto.GeoFixedObjectCollectingSeriesDTO;

@RestController
@RequestMapping(value = "${controller.mapping.OBJECTCOLLECTING_BY_ACTIVITY}")
public class GeoFixedObjectCollectingSeriesController extends RWGeoDataController<FixedSurvey, GeoLineStringData, GeoFixedObjectCollectingSeriesDTO, DataQueryDTO>{

	@Autowired
	public GeoFixedObjectCollectingSeriesController(FixedSurveyObjectCollectingService service, GeoFixedObjectCollectingSeriesESService serviceES) {
		super(service, serviceES);
	}
	
	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(GeoFixedSeriesQueryUtils.getFieldsExcludedOnQuery());
	}
	
	@Override
	public SuperDTO add(@PathVariable("activityId") String activityId, @Valid @RequestBody GeoFixedObjectCollectingSeriesDTO dto,
			BindingResult errorDto) {
		dto.getProperties().getSite().setPrefixType(DataPrefixType.OBJECT_COLLECTING);
		return super.add(activityId, dto, errorDto);
	}
	
	@Override
	public SuperDTO update(@Valid @RequestBody GeoFixedObjectCollectingSeriesDTO dto, BindingResult errorDto,
			@PathVariable("activityId") String activityId, @PathVariable("id") String id, HttpServletResponse response) {
		
		dto.getProperties().getSite().setPrefixType(DataPrefixType.OBJECT_COLLECTING);
		return super.update(dto, errorDto, activityId, id, response);
	}
}
