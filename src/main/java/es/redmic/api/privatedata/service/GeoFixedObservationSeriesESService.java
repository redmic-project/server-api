package es.redmic.api.privatedata.service;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;

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

import es.redmic.api.privatedata.repository.GeoFixedObservationSeriesESRepository;
import es.redmic.es.geodata.common.service.RGeoDataESService;
import es.redmic.models.es.common.DataPrefixType;
import es.redmic.models.es.common.model.ReferencesES;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.common.model.Feature;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.models.es.geojson.common.model.GeoSearchWrapper;
import es.redmic.models.es.geojson.geofixedstation.dto.GeoFixedTimeSeriesDTO;
import es.redmic.models.es.geojson.properties.model.GeoDataProperties;
import es.redmic.models.es.geojson.properties.model.Measurement;

@Service
public class GeoFixedObservationSeriesESService extends RGeoDataESService<GeoFixedTimeSeriesDTO, GeoPointData> {

	GeoFixedObservationSeriesESRepository repository;

	@Autowired
	public GeoFixedObservationSeriesESService(GeoFixedObservationSeriesESRepository repository) {
		super(repository);
		this.repository = repository;
	}

	public <T extends Geometry> GeoSearchWrapper<GeoDataProperties, T> findByDataDefinition(Long dataDefinitionId) {
		return repository.findByDataDefinition(dataDefinitionId);
	}

	@SuppressWarnings({ "unchecked" })
	public List<String> getDescendantsIds(List<String> parentsPath) {

		List<String> ids = new ArrayList<>();

		int size = parentsPath.size();
		List<String> paths = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			String idProperty = parentsPath.get(i);
			if (idProperty.split("\\.").length == 2)
				paths.add(idProperty);
			else
				ids.add(idProperty);
		}

		if (!paths.isEmpty()) {

			GeoDataQueryDTO query = new GeoDataQueryDTO();
			query.setDataType(DataPrefixType.getPrefixTypeFromClass(typeOfTDTO));
			List<String> returnFields = new ArrayList<>();
			returnFields.add("id");
			returnFields.add("uuid");
			returnFields.add("properties");
			query.setReturnFields(returnFields);
			query.addTerm("ids", paths);

			GeoSearchWrapper<GeoDataProperties, Point> response = (GeoSearchWrapper<GeoDataProperties, Point>) repository.find(query);

			if (response != null) {
				List<Feature<GeoDataProperties, Point>> features = response.getSourceList();

				if (features.isEmpty())
					return parentsPath;

				for (int i = 0; i < features.size(); i++) {
					List<Measurement> measurements = features.get(i).getProperties().getMeasurements();
					for (int j=0; j<measurements.size(); j++)
						ids.add(measurements.get(j).getParameter().getPath());
				}
			}
		}
		return ids;
	}
}
