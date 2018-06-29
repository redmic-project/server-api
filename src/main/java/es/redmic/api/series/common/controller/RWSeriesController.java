package es.redmic.api.series.common.controller;

import java.lang.reflect.ParameterizedType;
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
import es.redmic.db.series.common.service.RWBaseSeriesService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.series.common.dto.SeriesBaseDTO;

public abstract class RWSeriesController<TModel extends LongModel, TDTO extends SeriesBaseDTO> {
	
	@Autowired
	JsonSchemaService jsonSchemaService;
	
	private Class<TDTO> typeOfTDTO;

	private RWBaseSeriesService<TModel, TDTO> service;

	@SuppressWarnings("unchecked")
	public RWSeriesController(RWBaseSeriesService<TModel, TDTO> service) {
		this.service = service;
		this.typeOfTDTO = (Class<TDTO>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO addSeries(@Valid @RequestBody TDTO dto, BindingResult errorDto,
			@PathVariable("dataDefinitionId") Long dataDefinitionId) {
		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		dto.setDataDefinition(dataDefinitionId);

		return new BodyItemDTO<TDTO>(service.save(dto));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO updateSeries(@Valid @RequestBody TDTO dto, BindingResult errorDto,
			@PathVariable("id") Long id, @PathVariable("dataDefinitionId") Long dataDefinitionId) {
		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);
		dto.setId(id);
		dto.setDataDefinition(dataDefinitionId);
		return new BodyItemDTO<TDTO>(service.update(dto));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public SuperDTO delete(@PathVariable("dataDefinitionId") Long dataDefinitionId, @PathVariable("id") Long id) {
		service.delete(id);
		return new SuperDTO(true);
	}
	
	@RequestMapping(value = "${controller.mapping.EDIT_SCHEMA}", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String,Object> getJsonSchema(HttpServletResponse response) {
		
		return jsonSchemaService.getJsonSchema(typeOfTDTO.getName());
	}
}
