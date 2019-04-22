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

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import es.redmic.databaselib.common.model.LongModel;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.models.es.common.dto.SuperDTO;

public interface IRWController<TDTO extends DTO, TModel extends LongModel> {

	public SuperDTO update(@Valid @RequestBody TDTO dto, BindingResult bindingResult, @PathVariable("id") Long id,
			HttpServletResponse response);

	public SuperDTO delete(@PathVariable("id") Long id);

	public SuperDTO add(@Valid @RequestBody TDTO dto, BindingResult bindingResult);
	
	public HashMap<String,Object> getJsonSchema(HttpServletResponse response);
}
