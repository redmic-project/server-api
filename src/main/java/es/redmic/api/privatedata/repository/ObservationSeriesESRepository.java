package es.redmic.api.privatedata.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.InnerHitBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Value;

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

import org.springframework.stereotype.Repository;

import es.redmic.api.privatedata.model.ObservationSeries;
import es.redmic.es.data.common.repository.RDataESRepository;
import es.redmic.models.es.data.common.model.DataSearchWrapper;

@Repository
public class ObservationSeriesESRepository extends RDataESRepository<ObservationSeries> {

	protected static String[] INDEX = { "private-geodata" };
	protected static String TYPE = "_doc";

	public ObservationSeriesESRepository() {
		super(INDEX, TYPE);
	}

	@Override
	protected String getMappingFilePath(String index, String type) {
		return MAPPING_BASE_PATH + "private/geodata" + MAPPING_FILE_EXTENSION;
	}

	public DataSearchWrapper<?> findByDataDefinition(Long dataDefinitionId) {

		return findBy(QueryBuilders.termQuery("dataDefinition", dataDefinitionId));
	}

	/*
	 * Función que sobrescribe a getTermQuery de RElasticSearchRepository para
	 * añadir implementación específica para crear una query a apartir de una serie
	 * de términos obtenidos por el controlador.
	 */
	@Override
	public QueryBuilder getTermQuery(Map<String, Object> terms, BoolQueryBuilder query) {

		if (terms.containsKey("dataDefinition")) {
			Long dataDefinitionId = (Long) terms.get("dataDefinition");
			query.must(QueryBuilders.termQuery("dataDefinition", dataDefinitionId));
		}
		return super.getTermQuery(terms, query);
	}

	@Override
	protected String[] getDefaultSearchFields() {
		return new String[] { "observation.note.suggest" };
	}

	@Override
	protected String[] getDefaultHighlightFields() {
		return new String[] { "observation.note.suggest" };
	}

	@Override
	protected String[] getDefaultSuggestFields() {
		return new String[] { "observation.note" };
	}
}
