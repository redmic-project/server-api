package es.redmic.api.toponym.controller;

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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.api.geodata.common.controller.IRWGeoDataController;
import es.redmic.db.geodata.toponym.model.Toponym;
import es.redmic.db.geodata.toponym.service.ToponymService;
import es.redmic.es.toponym.service.ToponymESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.common.query.dto.MgetDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.toponym.dto.ToponymDTO;

@RestController
@RequestMapping(value = "${controller.mapping.TOPONYM}")
public class ToponymController extends RBaseController<es.redmic.models.es.geojson.toponym.model.Toponym, ToponymDTO, GeoDataQueryDTO>
		implements IRWGeoDataController<Toponym, ToponymDTO> {

	ToponymService service;

	ToponymESService serviceES;

	@Autowired
	public ToponymController(ToponymESService serviceES, ToponymService service) {
		super(serviceES);
		this.serviceES = serviceES;
		this.service = service;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO findById(@PathVariable("id") String id) {

		ToponymDTO item = serviceES.get(id, null);
		return new ElasticSearchDTO(item, item != null && item.getId() != null ? 1 : 0);
	}

	@RequestMapping(value = "/_mget", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _mget(@Valid @RequestBody MgetDTO mgetDto, BindingResult errorDto) {

		if (errorDto.hasErrors())
			throw new DTONotValidException(errorDto);

		return new ElasticSearchDTO(serviceES.mget(mgetDto, null));
	}

	@RequestMapping(value = "/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _search(@Valid @RequestBody GeoDataQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);

		return new ElasticSearchDTO(serviceES.find(orikaMapper.getMapperFacade().map(queryDTO, DataQueryDTO.class), null));
	}

	@RequestMapping(value = "/_suggest", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO _suggest(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam("text") String text, @RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = serviceES.createSimpleQueryDTOFromSuggestQueryParams(fields, text, size);
		processQuery((GeoDataQueryDTO) queryDTO);
		List<String> response = serviceES.suggest(convertToQuery((DataQueryDTO)queryDTO));

		return new ElasticSearchDTO(response, response.size());
	}
	
	@RequestMapping(value = "/_suggest", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _suggest(@Valid @RequestBody GeoDataQueryDTO queryDTO,
			BindingResult bindingResult) {
		
		processQuery(queryDTO, bindingResult);

		List<String> response = serviceES.suggest(convertToQuery(queryDTO));

		return new ElasticSearchDTO(response, response.size());
	}

	@Override
	public SuperDTO add(String activityId, ToponymDTO dto, BindingResult errorDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuperDTO update(ToponymDTO dto, BindingResult errorDto, String activityId, String id,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SuperDTO delete(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
