package es.redmic.api.common.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import es.redmic.models.es.common.dto.BaseDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.model.BaseES;
import es.redmic.models.es.common.query.dto.MgetDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

public interface IRController <TModel extends BaseES<?>, TDTO extends BaseDTO<?>, TQueryDTO extends SimpleQueryDTO> {
	
	public SuperDTO _search(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size);
	
	public SuperDTO _advancedSearch(@RequestBody TQueryDTO dto, BindingResult bindingResult);
	
	public SuperDTO _mget(@RequestBody MgetDTO dto, BindingResult bindingResult);
	
	public SuperDTO _get(@PathVariable("id") Long id);
	
	public SuperDTO _suggest(@RequestParam("fields") String[] fields, @RequestParam("text") String text,
			@RequestParam(required = false, value = "size") Integer size);

}
