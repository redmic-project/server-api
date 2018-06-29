package es.redmic.api.common.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.es.data.common.service.RDataESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.BaseDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.JSONCollectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.model.BaseES;
import es.redmic.models.es.common.query.dto.MgetDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

public abstract class RController<TModel extends BaseES<?>, TDTO extends BaseDTO<?>, TQueryDTO extends SimpleQueryDTO>
		extends SelectionWorkController<TModel, TDTO, TQueryDTO> implements IRController<TModel, TDTO, TQueryDTO> {

	protected RDataESService<TModel, TDTO> ESService;
	
	public RController(RDataESService<TModel, TDTO> service) {
		super(service);
		ESService = service;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _search(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = ESService.createSimpleQueryDTOFromTextQueryParams(fields, text, from, size);
		processQuery((TQueryDTO) queryDTO);
		JSONCollectionDTO result = ESService.find(convertToQuery((TQueryDTO) queryDTO));
		return new ElasticSearchDTO(result, result.getTotal());
	}

	@RequestMapping(value = "/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _advancedSearch(@Valid @RequestBody TQueryDTO queryDTO, BindingResult bindingResult) {
		
		processQuery(queryDTO, bindingResult);

		JSONCollectionDTO result = ESService.find(convertToQuery(queryDTO));
		return new ElasticSearchDTO(result, result.getTotal());
	}

	@RequestMapping(value = "/_mget", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _mget(@Valid @RequestBody MgetDTO dto, BindingResult errorDto) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		JSONCollectionDTO result = ESService.mget(dto);
		return new ElasticSearchDTO(result, result.getTotal());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _get(@PathVariable("id") Long id) {

		TDTO response = ESService.get(id.toString());
		return new ElasticSearchDTO(response, response == null ? 0 : 1);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/_suggest", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _suggest(@RequestParam(required = false, value = "fields") String[] fields, @RequestParam("text") String text,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = ESService.createSimpleQueryDTOFromSuggestQueryParams(fields, text, size);
		processQuery((TQueryDTO) queryDTO);
		List<String> response = ESService.suggest(convertToQuery((TQueryDTO) queryDTO));
		return new ElasticSearchDTO(response, response.size());
	}
	
	@RequestMapping(value = "/_suggest", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _advancedSuggest(@Valid @RequestBody TQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);
		List<String> response = ESService.suggest(convertToQuery((TQueryDTO) queryDTO));
		return new ElasticSearchDTO(response, response.size());
	}
}
