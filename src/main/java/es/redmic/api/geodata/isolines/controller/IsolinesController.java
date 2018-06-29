package es.redmic.api.geodata.isolines.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.geodata.common.controller.RWGeoDataController;
import es.redmic.db.geodata.isolines.model.Isolines;
import es.redmic.db.geodata.isolines.service.IsolinesService;
import es.redmic.es.common.queryFactory.geodata.IsolinesQueryUtils;
import es.redmic.es.geodata.isolines.service.IsolinesESService;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoMultiLineStringData;
import es.redmic.models.es.geojson.isolines.dto.IsolinesDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ISOLINES_BY_ACTIVITY}")
public class IsolinesController
		extends RWGeoDataController<Isolines, GeoMultiLineStringData, IsolinesDTO, DataQueryDTO> {
	
	@Autowired
	public IsolinesController(IsolinesService service, IsolinesESService ESService) {
		super(service, ESService);
	}
	
	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(IsolinesQueryUtils.getFieldsExcludedOnQuery());
	}
}
