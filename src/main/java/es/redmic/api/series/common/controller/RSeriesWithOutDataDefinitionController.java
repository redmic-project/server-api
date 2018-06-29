package es.redmic.api.series.common.controller;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.es.series.common.service.RSeriesESService;
import es.redmic.exception.custom.ResourceNotFoundException;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.JSONCollectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.series.common.dto.SeriesCommonDTO;
import es.redmic.models.es.series.common.model.SeriesCommon;

public abstract class RSeriesWithOutDataDefinitionController<TModel extends SeriesCommon, TDTO extends SeriesCommonDTO, TQueryDTO extends SimpleQueryDTO>
		extends RBaseController<TModel, TDTO, TQueryDTO> {

	protected RSeriesESService<TModel, TDTO> ESService;

	public RSeriesWithOutDataDefinitionController(RSeriesESService<TModel, TDTO> service) {
		super(service);
		ESService = service;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _search(@PathVariable("activityId") String activityId, @PathVariable("uuid") String uuid,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = ESService.createSimpleQueryDTOFromQueryParams(from, size);
		processQuery((TQueryDTO) queryDTO);
		return new ElasticSearchDTO(ESService.find(convertToQuery((TQueryDTO) queryDTO), uuid, activityId));
	}

	@RequestMapping(value = "/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _advancedSearch(@PathVariable("activityId") String activityId, @PathVariable("uuid") String uuid,
			@Valid @RequestBody TQueryDTO queryDTO, BindingResult errorDto) {

		processQuery(queryDTO, errorDto);

		JSONCollectionDTO result = ESService.find(convertToQuery(queryDTO), uuid, activityId);
		return new ElasticSearchDTO(result, result.getTotal());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _get(@PathVariable("activityId") String activityId, @PathVariable("uuid") String uuid,
			@PathVariable("id") String id) {

		TDTO result = ESService.get(id, uuid, activityId);
		return new ElasticSearchDTO(result, result == null ? 0 : 1);
	}

	@RequestMapping(value = "/_suggest", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _suggest() {

		throw new ResourceNotFoundException();
	}
}
