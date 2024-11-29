package es.redmic.api.privatedata.service;



/*-
 * #%L
 * API
 * %%
 * Copyright (C) 2024 REDMIC Project / Server
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
import org.springframework.stereotype.Service;

import es.redmic.api.privatedata.dto.ObservationSeriesDTO;
import es.redmic.api.privatedata.model.ObservationSeries;
import es.redmic.api.privatedata.repository.ObservationSeriesESRepository;
import es.redmic.es.data.common.service.RDataESService;

@Service
public class ObservationSeriesESService extends RDataESService<ObservationSeries, ObservationSeriesDTO> {

	ObservationSeriesESRepository repository;

	@Autowired
	public ObservationSeriesESService(ObservationSeriesESRepository repository) {
		super(repository);
		this.repository = repository;
	}
}
