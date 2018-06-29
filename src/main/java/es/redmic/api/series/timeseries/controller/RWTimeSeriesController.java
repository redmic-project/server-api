package es.redmic.api.series.timeseries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.series.common.controller.RWSeriesController;
import es.redmic.db.series.timeseries.model.TimeSeries;
import es.redmic.db.series.timeseries.service.TimeSeriesService;
import es.redmic.models.es.series.timeseries.dto.TimeSeriesDTO;

@RestController
@RequestMapping(value = "${controller.mapping.TIMESERIES_BY_DATA_DEFINITION}")
public class RWTimeSeriesController extends RWSeriesController<TimeSeries, TimeSeriesDTO> {

	@Autowired
	public RWTimeSeriesController(TimeSeriesService service) {
		super(service);
	}
}
