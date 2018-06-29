package es.redmic.api.maintenance.parameter.controller;

import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.api.common.controller.RController;
import es.redmic.es.maintenance.parameter.service.MetricDefinitionESService;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.parameter.dto.MetricDefinitionDTO;
import es.redmic.models.es.maintenance.parameter.model.MetricDefinition;

// TODO: extender de rw cuando esté implementado el servicio de db.

//@RestController
//@RequestMapping(value = "${controller.mapping.METRIC_DEFINITION}")
public class RMetricDefinitionController extends RController<MetricDefinition, MetricDefinitionDTO, MetadataQueryDTO> {

	@Autowired
	public RMetricDefinitionController(MetricDefinitionESService service) {
		super(service);
	}
}