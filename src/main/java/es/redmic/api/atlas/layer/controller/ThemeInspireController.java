package es.redmic.api.atlas.layer.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.atlas.layer.model.ThemeInspire;
import es.redmic.db.atlas.layer.service.ThemeInspireService;
import es.redmic.es.atlas.service.ThemeInspireESService;
import es.redmic.models.es.atlas.dto.ThemeInspireDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.THEME_INSPIRE}")
public class ThemeInspireController extends
		RWController<ThemeInspire, es.redmic.models.es.atlas.model.ThemeInspire, ThemeInspireDTO, SimpleQueryDTO> {

	@Autowired
	public ThemeInspireController(ThemeInspireService service, ThemeInspireESService serviceES) {
		super(service, serviceES);
	}
}
