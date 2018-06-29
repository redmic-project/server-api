package es.redmic.api.geodata.area.controller;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.geodata.common.controller.RWGeoDataController;
import es.redmic.db.geodata.area.model.Area;
import es.redmic.db.geodata.area.service.AreaService;
import es.redmic.es.common.queryFactory.geodata.AreaQueryUtils;
import es.redmic.es.geodata.area.service.AreaESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.area.dto.AreaDTO;
import es.redmic.models.es.geojson.area.dto.AreaPropertiesDTO;
import es.redmic.models.es.geojson.common.model.GeoMultiPolygonData;

@RestController
@RequestMapping(value = "${controller.mapping.AREAS_BY_ACTIVITY}")
public class AreaController extends RWGeoDataController<Area, GeoMultiPolygonData, AreaDTO, GeoDataQueryDTO> {

	private AreaService service;

	private AreaESService ESService;

	@Autowired
	public AreaController(AreaService service, AreaESService ESService) {
		super(service, ESService);
		this.service = service;
		this.ESService = ESService;
	}

	@PostConstruct
	private void postConstruct() {
		setFieldsExcludedOnQuery(AreaQueryUtils.getFieldsExcludedOnQuery());
	}

	@RequestMapping(value = "/properties/{uuid}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO findPropertiesById(@PathVariable("activityId") String activityId,
			@PathVariable("uuid") String uuid) {

		AreaDTO item = ESService.get(uuid, activityId);

		if (item != null && item.getId() != null)
			return new ElasticSearchDTO(item.getProperties(), 1);
		else
			return new ElasticSearchDTO(null, 0);
	}

	@RequestMapping(value = "/properties/{uuid}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO updateProperties(@Valid @RequestBody AreaPropertiesDTO propertiesDTO, BindingResult errorDto,
			@PathVariable("activityId") String activityId, @PathVariable("uuid") String uuid,
			HttpServletResponse response) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		service.checkDataType(activityId);

		propertiesDTO.setActivityId(activityId);

		return new BodyItemDTO<AreaDTO>(service.updateProperties(propertiesDTO, uuid));
	}

	@RequestMapping(value = "/properties/_schema", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> getPropertiesJsonSchema(HttpServletResponse response) {

		return jsonSchemaService.getJsonSchema(AreaPropertiesDTO.class.getName());
	}
}
