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

import org.springframework.beans.factory.annotation.Autowired;

import es.redmic.api.common.controller.RController;
import es.redmic.es.maintenance.parameter.service.MetricDefinitionESService;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.parameter.dto.MetricDefinitionDTO;
import es.redmic.models.es.maintenance.parameter.model.MetricDefinition;

// TODO: extender de rw cuando esté implementado el servicio de db.

//@RestController
//@RequestMapping(value = "${controller.mapping.METRIC_DEFINITION}")
public class RMetricDefinitionController extends RController<MetricDefinition, MetricDefinitionDTO, MetadataQueryDTO> {

	@Autowired
	public RMetricDefinitionController(MetricDefinitionESService service) {
		super(service);
	}
}
