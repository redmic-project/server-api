package es.redmic.api.series.infrastructureattributes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.series.common.controller.RWSeriesWithOutDataDefinitionController;
import es.redmic.db.series.infrastructureattributes.model.InfrastructureAttributes;
import es.redmic.db.series.infrastructureattributes.service.InfrastructureAttributesService;
import es.redmic.es.series.attributeseries.service.AttributeSeriesESService;
import es.redmic.models.es.common.query.dto.DataAccessibilityQueryDTO;
import es.redmic.models.es.series.attributeseries.dto.AttributeSeriesDTO;
import es.redmic.models.es.series.attributeseries.model.AttributeSeries;

@RestController
@RequestMapping(value = "${controller.mapping.INFRASTRUCTUREATTRIBUTES}")
public class InfrastructureAttributesController extends
		RWSeriesWithOutDataDefinitionController<InfrastructureAttributes, AttributeSeries, AttributeSeriesDTO, DataAccessibilityQueryDTO> {

	@Autowired
	public InfrastructureAttributesController(InfrastructureAttributesService service,
			AttributeSeriesESService serviceES) {
		super(service, serviceES);
	}
}
