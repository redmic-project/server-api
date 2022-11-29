package es.redmic.api.utils.filters;

/*-
 * #%L
 * API
 * %%
 * Copyright (C) 2019 - 2022 REDMIC Project / Server
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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import es.redmic.es.common.service.UserUtilsServiceItfc;
import es.redmic.models.es.administrative.dto.DocumentDTO;

public class DTOFilters {

	public static SimpleBeanPropertyFilter getDocumentFilter(UserUtilsServiceItfc userService) {

		return new SimpleBeanPropertyFilter() {
			@Override
			public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {
				if (include(writer)) {
					List<String> roles = userService.getUserRole();
					if (writer.getName().equals("internalUrl")) {

						setInternalUrlFilter((DocumentDTO) pojo, roles);
					}
					writer.serializeAsField(pojo, jgen, provider);
				} else if (!jgen.canOmitFields()) { // since 2.3
					writer.serializeAsOmittedField(pojo, jgen, provider);
				}
			}
			@Override
			protected boolean include(BeanPropertyWriter writer) {
			   return true;
			}
			@Override
			protected boolean include(PropertyWriter writer) {
			   return true;
			}
		 };
	}

	private static void setInternalUrlFilter(DocumentDTO document, List<String> roles) {

		Boolean privateInternalUrl = document.getPrivateInternalUrl();

		if (!roles.contains("ROLE_ADMINISTRATOR") && Boolean.TRUE.equals(privateInternalUrl)) {
			document.setInternalUrl(null);
		}
	}
}
