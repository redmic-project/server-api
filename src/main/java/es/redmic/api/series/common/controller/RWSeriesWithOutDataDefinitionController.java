package es.redmic.api.series.common.controller;

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
import es.redmic.db.series.common.service.SeriesService;
import es.redmic.es.series.common.service.RWSeriesESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.series.common.dto.SeriesCommonDTO;
import es.redmic.models.es.series.common.model.SeriesCommon;

public abstract class RWSeriesWithOutDataDefinitionController<TDBModel extends LongModel, TModel extends SeriesCommon, TDTO extends SeriesCommonDTO, TQueryDTO extends SimpleQueryDTO>
		extends RSeriesWithOutDataDefinitionController<TModel, TDTO, TQueryDTO> {

	@Autowired
	JsonSchemaService jsonSchemaService;

	private SeriesService<TDBModel, TDTO> service;

	public RWSeriesWithOutDataDefinitionController(SeriesService<TDBModel, TDTO> service,
			RWSeriesESService<TModel, TDTO> serviceES) {
		super(serviceES);
		this.service = service;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO addSeries(@Valid @RequestBody TDTO dto, BindingResult errorDto,
			@PathVariable("activityId") String activityId, @PathVariable("uuid") String uuid) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		dto.set_grandparentId(activityId);
		dto.set_parentId(uuid);

		return new BodyItemDTO<TDTO>(service.save(dto));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO updateSeries(@Valid @RequestBody TDTO dto, BindingResult errorDto, @PathVariable("id") Long id,
			@PathVariable("activityId") String activityId, @PathVariable("uuid") String uuid) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		dto.setId(id);
		dto.set_grandparentId(activityId);
		dto.set_parentId(uuid);

		return new BodyItemDTO<TDTO>(service.update(dto));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public SuperDTO delete(@PathVariable("id") Long id) {

		service.delete(id);

		return new SuperDTO(true);
	}

	@RequestMapping(value = "${controller.mapping.EDIT_SCHEMA}", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> getJsonSchema(HttpServletResponse response) {

		return jsonSchemaService.getJsonSchema(typeOfTDTO.getName());
	}
}
