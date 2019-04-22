package es.redmic.api.series.timeseries.controller;

/*-
 * #%L
 * API
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.api.common.controller.ISettingsController;
import es.redmic.api.common.controller.RController;
import es.redmic.es.data.common.service.RWDataESService;
import es.redmic.es.series.timeseries.service.TimeSeriesSettingsESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.BaseDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.model.BaseES;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.series.timeseries.dto.TimeSeriesSettingsDTO;

public abstract class TimeSeriesSettingsController<TModel extends BaseES<?>, TDTO extends BaseDTO<?>>
		extends RController<TModel, TDTO, SimpleQueryDTO> implements ISettingsController {
	
	private String serviceName;
	
	@Autowired
	TimeSeriesSettingsESService settingsService;
	
	public TimeSeriesSettingsController(RWDataESService<TModel, TDTO> service, String serviceName) {
		super(service);
		this.serviceName = serviceName;
	}

	@RequestMapping(value = "/histogram/settings", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO saveSettings(@Valid @RequestBody TimeSeriesSettingsDTO dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);
		// TODO: el servicio debe devolver un dto
		return new ElasticSearchDTO(settingsService.save(dto), 1);
	}

	@RequestMapping(value = "/histogram/settings/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO updateSettings(@PathVariable("id") String id, @Valid @RequestBody TimeSeriesSettingsDTO dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);
		dto.setId(id);
		// TODO: el servicio debe devolver un dto
		return new ElasticSearchDTO(settingsService.update(dto), 1);
	}

	@RequestMapping(value = "/histogram/settings/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO findAllSettings(@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult) {
		
		processQuery(queryDTO, bindingResult);
		return new ElasticSearchDTO(settingsService.findAll(queryDTO, serviceName));
	}

	@RequestMapping(value = "/histogram/settings/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO getSettings(@PathVariable("id") String id) {

		TimeSeriesSettingsDTO response = settingsService.get(id.toString());
		return new ElasticSearchDTO(response, response == null ? 0 : 1);
	}

	@RequestMapping(value = "/histogram/settings/_suggest", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO suggestSettings(@RequestParam("fields") String[] fields, @RequestParam("text") String text,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = settingsService.createSimpleQueryDTOFromSuggestQueryParams(fields, text, size);
		processQuery((SimpleQueryDTO) queryDTO);
		List<String> response = settingsService.suggest(convertToQuery((SimpleQueryDTO) queryDTO));
		
		return new ElasticSearchDTO(response, response.size());
	}

	@RequestMapping(value = "/histogram/settings/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public SuperDTO deleteSettings(@PathVariable("id") String id) {

		settingsService.delete(id);
		return new SuperDTO(true);
	}
}
