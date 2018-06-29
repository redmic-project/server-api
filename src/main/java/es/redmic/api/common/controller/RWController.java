package es.redmic.api.common.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.api.common.service.JsonSchemaService;
import es.redmic.databaselib.common.model.LongModel;
import es.redmic.db.common.service.IServiceRW;
import es.redmic.es.data.common.service.RWDataESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.model.BaseAbstractES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

public abstract class RWController<TModel extends LongModel, TESModel extends BaseAbstractES, TDTO extends DTO, TQueryDTO extends SimpleQueryDTO>
		extends RController<TESModel, TDTO, TQueryDTO> implements IRWController<TDTO, TModel> {
	
	@Autowired
	JsonSchemaService jsonSchemaService;

	private final IServiceRW<TModel, TDTO> service;

	public RWController(IServiceRW<TModel, TDTO> service, RWDataESService<TESModel, TDTO> serviceES) {
		super(serviceES);
		this.service = service;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO update(@Valid @RequestBody TDTO dto, BindingResult errorDto, @PathVariable("id") Long id,
			HttpServletResponse response) {
		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);
		dto.setId(id);
		return new BodyItemDTO<TDTO>(service.update(dto));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public SuperDTO delete(@PathVariable("id") Long id) {
		service.delete(id);
		return new SuperDTO(true);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO add(@Valid @RequestBody TDTO dto, BindingResult errorDto) {
		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);
		return new BodyItemDTO<TDTO>(service.save(dto));
	}
	
	@RequestMapping(value = "${controller.mapping.EDIT_SCHEMA}", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String,Object> getJsonSchema(HttpServletResponse response) {
		
		return jsonSchemaService.getJsonSchema(typeOfTDTO.getName());
	}
}