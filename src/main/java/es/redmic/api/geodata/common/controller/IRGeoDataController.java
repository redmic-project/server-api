package es.redmic.api.geodata.common.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.MgetDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public interface IRGeoDataController<TModel extends Feature<GeoDataProperties, ?>, TDTO extends MetaFeatureDTO<?,?>, TQueryDTO extends SimpleQueryDTO> {

	public SuperDTO findById(String activityId, String id);
	public SuperDTO _mget(String activityId, MgetDTO mgetDto, BindingResult errorDto);
	public SuperDTO _search(String activityId, @RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size);
	public SuperDTO _advancedSearch(String activityId, TQueryDTO query, BindingResult errorDto);
	public SuperDTO _suggest(String activityId, String[] fields, String text, Integer size);
}
