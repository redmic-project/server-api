package es.redmic.api.privatedata.repository;

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

import java.util.Map;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.stereotype.Repository;

import es.redmic.api.privatedata.model.ObservationSeries;
import es.redmic.es.data.common.repository.RDataESRepository;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.DateLimitsDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.data.common.model.DataSearchWrapper;

@Repository
public class ObservationSeriesESRepository extends RDataESRepository<ObservationSeries> {

	protected static String[] INDEX = { "private-observationseries" };
	protected static String TYPE = "_doc";

	public static final String DATETIME_FIELD = "date";

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
			Long dataDefinitionId = Long.valueOf(((Integer)terms.get("dataDefinition")).longValue());
			query.must(QueryBuilders.termQuery("dataDefinition", dataDefinitionId));
		}
		return super.getTermQuery(terms, query);
	}

	@Override
	protected String[] getDefaultSearchFields() {
		return new String[] { "observation.animal.name", "observation.animal.name.suggest",
			"observation.device.name", "observation.device.name.suggest",
			"observation.organisation.name", "observation.organisation.name.suggest",
			"observation.taxonomy.scientificName", "observation.taxonomy.scientificName.suggest" };
	}

	@Override
	protected String[] getDefaultHighlightFields() {
		return new String[] { "observation.animal.name", "observation.animal.name.suggest",
			"observation.device.name", "observation.device.name.suggest",
			"observation.organisation.name", "observation.organisation.name.suggest",
			"observation.taxonomy.scientificName", "observation.taxonomy.scientificName.suggest" };
	}

	@Override
	protected String[] getDefaultSuggestFields() {
		return new String[] { "observation.animal.name", "observation.device.name",
			"observation.organisation.name", "observation.taxonomy.scientificName" };
	}

	// TODO: ELiminar cuando se extienda de RSeriesRepository

	@Override
	protected <TQueryDTO extends SimpleQueryDTO> BoolQueryBuilder getQueryBuilder(TQueryDTO queryDTO, QueryBuilder serviceQuery) {

		BoolQueryBuilder query = super.getQueryBuilder(queryDTO, serviceQuery);

		QueryBuilder dateLimitsQuery = getDateLimitsQuery(((DataQueryDTO)queryDTO).getDateLimits(), DATETIME_FIELD);

		if (dateLimitsQuery != null) {
			query.must(dateLimitsQuery);
		}

		return query;
	}

	protected static QueryBuilder getDateLimitsQuery(DateLimitsDTO dateLimitsDTO, String datePath) {

		if (dateLimitsDTO == null)
			return null;

		RangeQueryBuilder range = QueryBuilders.rangeQuery(datePath);

		if (dateLimitsDTO.getStartDate() != null)
			range.gte(dateLimitsDTO.getStartDate());
		if (dateLimitsDTO.getEndDate() != null)
			range.lte(dateLimitsDTO.getEndDate());

		return range;
	}
}
