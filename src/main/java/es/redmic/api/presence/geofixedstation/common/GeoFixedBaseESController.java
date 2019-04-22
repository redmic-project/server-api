package es.redmic.api.presence.geofixedstation.common;

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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.api.common.controller.ISelectionWorkController;
import es.redmic.api.presence.geodata.common.GeoDataPresenceController;
import es.redmic.es.geodata.common.service.GeoDataSelectionWorkService;
import es.redmic.es.geodata.geofixedstation.service.GeoFixedBaseESService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SelectionWorkDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.geojson.common.dto.FixedSurveySeriesPropertiesDTO;
import es.redmic.models.es.geojson.common.dto.MetaFeatureDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;

public abstract class GeoFixedBaseESController<
		TModel extends Feature<GeoDataProperties, ?>, 
		TDTO extends MetaFeatureDTO<FixedSurveySeriesPropertiesDTO, ?>, 
		TQueryDTO extends SimpleQueryDTO>
			extends GeoDataPresenceController<TModel, TDTO, TQueryDTO> implements ISelectionWorkController {

	GeoFixedBaseESService<TDTO, TModel> serviceES;

	@Autowired
	GeoDataSelectionWorkService selectionWorkService;

	protected String serviceName;

	public GeoFixedBaseESController(GeoFixedBaseESService<TDTO, TModel> service, String serviceName) {
		super(service);
		this.serviceES = service;
		this.serviceName = serviceName;
	}

	@Override
	@RequestMapping(value = "${controller.mapping.SELECTION_WORK}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO getSelectionWork(@PathVariable("id") String id) {
		SelectionWorkDTO out = selectionWorkService.get(id);
		if (out != null && out.getIds() != null)
			return new ElasticSearchDTO(out, out.getIds().size());
		else
			return new ElasticSearchDTO(out, 0);
	}

	@Override
	@RequestMapping(value = "${controller.mapping.SELECTION_WORK}", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO saveSelectionWork(@Valid @RequestBody SelectionWorkDTO dto, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);
		dto.setService(serviceName);
		dto.setIdProperty("path");
		if (dto.getIds() != null && dto.getIds().size() > 0)
			dto.setIds(serviceES.getDescendantsIds(dto.getIds()));

		SelectionWorkDTO out = selectionWorkService.save(dto);
		if (out != null && out.getIds() != null)
			return new ElasticSearchDTO(out, out.getIds().size());
		else
			return new ElasticSearchDTO(out, 0);
	}

	@Override
	@RequestMapping(value = "${controller.mapping.SELECTION_WORK}/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO updateSelection(@Valid @RequestBody SelectionWorkDTO dto, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		dto.setService(serviceName);
		dto.setIdProperty("path");

		if (dto.getIds() != null && dto.getIds().size() > 0)
			dto.setIds(serviceES.getDescendantsIds(dto.getIds()));

		SelectionWorkDTO out = selectionWorkService.update(dto);
		if (out != null && out.getIds() != null)
			return new ElasticSearchDTO(out, out.getIds().size());
		else
			return new ElasticSearchDTO(out, 0);
	}
}
