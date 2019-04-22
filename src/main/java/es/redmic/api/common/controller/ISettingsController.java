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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import es.redmic.models.es.common.dto.SelectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;

public interface ISettingsController {

	public SuperDTO findAllSettings(@Valid @RequestBody DataQueryDTO dto, BindingResult bindingResult,
			HttpServletRequest request);

	public SuperDTO getSettings(@PathVariable("id") String id);

	public SuperDTO suggestSettings(@RequestParam("fields") String[] fields, @RequestParam("text") String text,
			@RequestParam(required = false, value = "size") Integer size);

	public SuperDTO saveSettings(@Valid @RequestBody SelectionDTO dto, BindingResult bindingResult,
			HttpServletRequest request);

	public SuperDTO updateSettings(@PathVariable("id") String id, @Valid @RequestBody SelectionDTO dto,
			BindingResult bindingResult, HttpServletRequest request);

	public SuperDTO deleteSettings(@PathVariable("id") String id);
}
