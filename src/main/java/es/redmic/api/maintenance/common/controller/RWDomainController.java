package es.redmic.api.maintenance.common.controller;

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

import es.redmic.api.common.controller.RWController;
import es.redmic.databaselib.common.model.LongModel;
import es.redmic.db.common.service.IServiceRW;
import es.redmic.es.data.common.service.RWDataESService;
import es.redmic.models.es.common.dto.DTO;
import es.redmic.models.es.common.model.BaseAbstractES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

public abstract class RWDomainController<TModel extends LongModel, TESModel extends BaseAbstractES, TDTO extends DTO, TQueryDTO extends SimpleQueryDTO>
		extends RWController<TModel, TESModel, TDTO, TQueryDTO> {

	public RWDomainController(IServiceRW<TModel, TDTO> service, RWDataESService<TESModel, TDTO> serviceES) {
		super(service, serviceES);
	}
}
