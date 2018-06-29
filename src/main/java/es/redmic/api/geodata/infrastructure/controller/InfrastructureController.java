package es.redmic.api.geodata.infrastructure.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.geodata.common.controller.RWGeoDataController;
import es.redmic.db.geodata.infrastructure.model.Infrastructure;
import es.redmic.db.geodata.infrastructure.service.InfrastructureService;
import es.redmic.es.common.queryFactory.geodata.InfrastructureQueryUtils;
import es.redmic.es.geodata.infrastructure.service.InfrastructureESService;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.infrastructure.dto.InfrastructureDTO;

@RestController
@RequestMapping(value = "${controller.mapping.INFRASTRUCTURES_BY_ACTIVITY}")
public class InfrastructureController
		extends RWGeoDataController<Infrastructure, GeoPointData, InfrastructureDTO, DataQueryDTO> {

	@Autowired
	public InfrastructureController(InfrastructureService service, InfrastructureESService ESService) {
		super(service, ESService);
	}
	
	@PostConstruct
	private void postConstruct() {
		
		setFieldsExcludedOnQuery(InfrastructureQueryUtils.getFieldsExcludedOnQuery());
	}
}
