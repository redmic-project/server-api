package es.redmic.api.series.infrastructureattributes.controller;

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

import es.redmic.api.series.common.controller.RWSeriesWithOutDataDefinitionController;
import es.redmic.db.series.infrastructureattributes.model.InfrastructureAttributes;
import es.redmic.db.series.infrastructureattributes.service.InfrastructureAttributesService;
import es.redmic.es.series.attributeseries.service.AttributeSeriesESService;
import es.redmic.models.es.common.query.dto.DataAccessibilityQueryDTO;
import es.redmic.models.es.series.attributeseries.dto.AttributeSeriesDTO;
import es.redmic.models.es.series.attributeseries.model.AttributeSeries;

@RestController
@RequestMapping(value = "${controller.mapping.INFRASTRUCTUREATTRIBUTES}")
public class InfrastructureAttributesController extends
		RWSeriesWithOutDataDefinitionController<InfrastructureAttributes, AttributeSeries, AttributeSeriesDTO, DataAccessibilityQueryDTO> {

	@Autowired
	public InfrastructureAttributesController(InfrastructureAttributesService service,
			AttributeSeriesESService serviceES) {
		super(service, serviceES);
	}
}
