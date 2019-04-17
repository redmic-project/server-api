package es.redmic.api.maintenance.objects.controller;

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
import es.redmic.db.maintenance.objects.model.ObjectType;
import es.redmic.db.maintenance.objects.service.ObjectTypeService;
import es.redmic.es.maintenance.objects.service.ObjectTypeESService;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.objects.dto.ObjectTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.OBJECT_TYPE}")
public class ObjectTypeController extends
		RWController<ObjectType, es.redmic.models.es.maintenance.objects.model.ObjectType, ObjectTypeDTO, MetadataQueryDTO> {

	@Autowired
	public ObjectTypeController(ObjectTypeService service, ObjectTypeESService serviceES) {
		super(service, serviceES);
	}
}
