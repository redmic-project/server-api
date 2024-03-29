package es.redmic.api.geodata.citation.service;

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

import java.util.Map;

import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.redmic.db.geodata.common.service.GridService;
import es.redmic.es.geodata.common.service.GridServiceItfc;
import es.redmic.es.tools.distributions.species.repository.RWTaxonDistributionRepository;
import es.redmic.models.es.tools.distribution.model.Distribution;

@Service
public class CitationGridService implements GridServiceItfc {

	@Autowired
	GridService gridService;

	@Autowired
	protected ObjectMapper objectMapper;

	public Distribution getDistribution(Geometry geometry, RWTaxonDistributionRepository repo, int radius) {

		Map<String, Object> result = gridService.findGridByPointAndPrecision(repo.getGridSize(),
				geometry.getCoordinate().x, geometry.getCoordinate().y, radius);
		if (result != null)
			return objectMapper.convertValue(result, Distribution.class);
		return null;
	}
}
