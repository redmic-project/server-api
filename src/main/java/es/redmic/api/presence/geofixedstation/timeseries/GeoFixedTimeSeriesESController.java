package es.redmic.api.presence.geofixedstation.timeseries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.presence.geofixedstation.common.GeoFixedBaseESController;
import es.redmic.es.geodata.geofixedstation.service.GeoFixedTimeSeriesESService;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.geofixedstation.dto.GeoFixedTimeSeriesDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SURVEYSTATIONS_TIMESERIES}")
public class GeoFixedTimeSeriesESController extends GeoFixedBaseESController<GeoPointData, GeoFixedTimeSeriesDTO, DataQueryDTO> {

	
	private static String serviceName = "SurveyParameters";

	@Autowired
	public GeoFixedTimeSeriesESController(GeoFixedTimeSeriesESService service) {
		super(service, serviceName);
	}
}