package es.redmic.api.maintenance.parameter.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.maintenance.parameter.model.Parameter;
import es.redmic.db.maintenance.parameter.service.ParameterService;
import es.redmic.es.maintenance.parameter.service.ParameterESService;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.parameter.dto.ParameterDTO;
import es.redmic.models.es.maintenance.parameter.model.Unit;

@RestController
@RequestMapping(value = "${controller.mapping.PARAMETER}")
public class ParameterController extends
		RWController<Parameter, es.redmic.models.es.maintenance.parameter.model.Parameter, ParameterDTO, MetadataQueryDTO> {

	private ParameterESService serviceES;

	@Autowired
	public ParameterController(ParameterService service, ParameterESService serviceES) {
		super(service, serviceES);
		this.serviceES = serviceES;
	}

	@RequestMapping(value = "/{parameterId}/units", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO findAll(@PathVariable("parameterId") String parameterId) {

		List<Unit> result = serviceES.getUnits(parameterId);
		return new ElasticSearchDTO(result, result.size());
	}
}
