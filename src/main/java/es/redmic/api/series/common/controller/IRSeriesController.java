package es.redmic.api.series.common.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public interface IRSeriesController<TModel extends Feature<GeoDataProperties, ?>, TDTO extends MetaFeatureDTO<?, ?>, TQueryDTO extends SimpleQueryDTO> {

	public SuperDTO _search(@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size);

	public SuperDTO _advancedSearch(@Valid @RequestBody TQueryDTO queryDTO, BindingResult errorDto);

	public SuperDTO _get(@PathVariable("id") String id);
}