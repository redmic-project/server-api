package es.redmic.api.series.objectcollecting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.series.common.controller.RWSeriesController;
import es.redmic.db.series.objectcollecting.model.ObjectCollecting;
import es.redmic.db.series.objectcollecting.service.ObjectCollectingSeriesService;
import es.redmic.models.es.series.objectcollecting.dto.ObjectCollectingSeriesDTO;

@RestController
@RequestMapping(value = "${controller.mapping.OBJECTCOLLECTING_BY_DATA_DEFINITION}")
public class RWObjectCollectingSeriesController extends RWSeriesController<ObjectCollecting, ObjectCollectingSeriesDTO> {

	@Autowired
	public RWObjectCollectingSeriesController(ObjectCollectingSeriesService service) {
		super(service);
	}
}
