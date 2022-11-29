package es.redmic.api.common.controller;

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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.api.common.service.UserUtilsService;
import es.redmic.es.common.service.RBaseESService;
import es.redmic.es.config.OrikaScanBeanESItfc;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.DataPrefixType;
import es.redmic.models.es.common.dto.BaseDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.model.BaseES;
import es.redmic.models.es.common.query.dto.DataAccessibilityQueryDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

public abstract class RBaseController<TModel extends BaseES<?>, TDTO extends BaseDTO<?>, TQueryDTO>
		implements IRBaseController<TModel, TDTO, TQueryDTO> {

	@Autowired
	protected OrikaScanBeanESItfc orikaMapper;

	@Autowired
	protected ObjectMapper objectMapper;

	private Map<String, Object> fixedQuery = new HashMap<>();

	RBaseESService<TModel, TDTO> service;

	@Autowired
	protected UserUtilsService userService;

	protected Class<TDTO> typeOfTDTO;
	protected Class<TQueryDTO> typeOfTQueryDTO;

	protected Set<String> fieldsExcludedOnQuery = new HashSet<>();

	protected RBaseController(RBaseESService<TModel, TDTO> service) {
		this.service = service;
		defineTypeOfArguments();
	}

	@GetMapping(value = { "${controller.mapping.FILTER_SCHEMA}", "${contoller.mapping.FILTERED_DOCUMENTS_SCHEMA}",
			"${contoller.mapping.ANCESTORS_SCHEMA}", "${controller.mapping.TRACK_CLUSTER_SCHEMA}" })
	@ResponseBody
	public ElasticSearchDTO getFilterSchema(HttpServletResponse response) {

		return service.getFilterSchema(typeOfTQueryDTO, fieldsExcludedOnQuery);
	}

	@SuppressWarnings("unchecked")
	private void defineTypeOfArguments() {

		int numberOfArguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments().length;
		Type[] arguments = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();

		switch (numberOfArguments) {
		case 4:
			this.typeOfTDTO = (Class<TDTO>) (arguments[2]);
			this.typeOfTQueryDTO = (Class<TQueryDTO>) (arguments[3]);
			break;
		case 3:
			this.typeOfTDTO = (Class<TDTO>) (arguments[1]);
			this.typeOfTQueryDTO = (Class<TQueryDTO>) (arguments[2]);
			break;
		}
	}

	protected void processQuery(TQueryDTO queryDTO) {
		processQuery(queryDTO, null);
	}

	protected void processQuery(TQueryDTO queryDTO, BindingResult bindingResult) {

		if (bindingResult != null && bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);

		checkFieldsExcludedOnQuery(queryDTO);

		checkAccessibility(queryDTO);

		if (getFixedQuery() != null && getFixedQuery().size() > 0)
			((SimpleQueryDTO) queryDTO).getTerms().putAll(getFixedQuery());

		((SimpleQueryDTO) queryDTO).setDataType(DataPrefixType.getPrefixTypeFromClass(typeOfTDTO));
	}

	private void checkFieldsExcludedOnQuery(TQueryDTO queryDTO) {

		// TODO: Inyectar de alguna manera los campos como excluidos del json y
		// que el propio jackson de el fallo.
		Class<?> queryClass = queryDTO.getClass();
		for (String fieldName : fieldsExcludedOnQuery) {

			Field field = getFieldFromClass(queryClass, fieldName);
			field.setAccessible(true);
			Object value;
			try {
				value = field.get(queryDTO);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new DTONotValidException(new BindException(queryDTO, fieldName));
			}
			if (value != null)
				throw new DTONotValidException(new BindException(queryDTO, fieldName));

		}
	}

	private Field getFieldFromClass(Class<?> queryClass, String fieldName) {

		try {
			return queryClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			return getFieldFromClass(queryClass.getSuperclass(), fieldName);
		}
	}

	// TODO: pasar comprobación a servicio?
	private void checkAccessibility(TQueryDTO queryDTO) {

		List<Long> accessibilities = userService.getAccessibilityControl();

		if (queryDTO instanceof DataAccessibilityQueryDTO) {
			((DataAccessibilityQueryDTO) queryDTO).addAccessibilityIds(accessibilities);
		} else if (queryDTO instanceof GeoDataQueryDTO) {
			((GeoDataQueryDTO) queryDTO).addAccessibilityIds(accessibilities);
		} else if (queryDTO instanceof DataQueryDTO) {
			((DataQueryDTO) queryDTO).addAccessibilityIds(accessibilities);
		}
	}

	protected void setFieldsExcludedOnQuery(Set<String> fieldsExcludedOnQuery) {

		this.fieldsExcludedOnQuery = fieldsExcludedOnQuery;
	}

	protected Map<String, Object> getFixedQuery() {
		return fixedQuery;
	}

	protected void setFixedQuery(Map<String, Object> fixedQuery) {
		this.fixedQuery.putAll(fixedQuery);
	}

	protected void setFixedQuery(String term, Object value) {

		fixedQuery.put(term, value);
	}

	protected DataQueryDTO convertToDataQuery(TQueryDTO queryDTO) {

		DataQueryDTO globalQuery = objectMapper.convertValue(queryDTO, DataQueryDTO.class);
		globalQuery.setDataType(((SimpleQueryDTO) queryDTO).getDataType());

		return globalQuery;
	}

	protected GeoDataQueryDTO convertToGeoDataQuery(TQueryDTO queryDTO) {

		GeoDataQueryDTO globalQuery = objectMapper.convertValue(queryDTO, GeoDataQueryDTO.class);
		globalQuery.setDataType(((SimpleQueryDTO) queryDTO).getDataType());

		return globalQuery;
	}
}
