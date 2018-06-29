package es.redmic.api.geodata.common.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;

public interface IRWGeoDataController<TDBModel extends LongModel, TDTO extends MetaFeatureDTO<?,?>> {

	public SuperDTO add(String activityId, TDTO dto, BindingResult errorDto);
	public SuperDTO update(TDTO dto, BindingResult errorDto, String activityId, String id, HttpServletResponse response);
	public SuperDTO delete(String id);
}