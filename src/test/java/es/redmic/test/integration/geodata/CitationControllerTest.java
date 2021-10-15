package es.redmic.test.integration.geodata;

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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import es.redmic.ApiApplication;
import es.redmic.es.geodata.citation.repository.CitationESRepository;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;
import es.redmic.test.integration.ApiApplicationTest;
import es.redmic.test.integration.common.IntegrationTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ApiApplication.class,
		ApiApplicationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CitationControllerTest extends IntegrationTestBase {

	private static final String RESOURCE_PATH = "/geo/models/citation.json";

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	private RestDocumentationResultHandler document;

	@Autowired
	CitationESRepository repository;

	private final String CITATIONS_BY_ACTIVITY_URL_BASE = "/activities/1167/citations";

	private final String CITATIONS_URL_BASE = "/citations";

	@Override
	@Before
	public void setUp() {

		// @formatter:off

		this.document = document("{class-name}/{method-name}", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()));

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain)
				.apply(documentationConfiguration(this.restDocumentation)).alwaysDo(this.document).build();

		// @formatter:on
	}

	@Test
	public void getCitationByActivitySchema_Return200_WhenSearchIsCorrect() throws Exception {

		// @formatter:off

		this.mockMvc
				.perform(get(CITATIONS_BY_ACTIVITY_URL_BASE + "/_search/_schema").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// @formatter:on
	}

	@Test
	public void getCitation_Return200_WhenSearchIsCorrect() throws Exception {

		GeoPointData modelToIndex = (GeoPointData) getModelToResource(RESOURCE_PATH, GeoPointData.class);
		repository.save(modelToIndex);

		// @formatter:off

		this.mockMvc
				.perform(get(CITATIONS_URL_BASE + "/" + modelToIndex.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// @formatter:on
	}

	@SuppressWarnings("unchecked")
	@Test
	public void searchCitationByActivity_Return200_WhenSearchIsCorrect() throws Exception {

		GeoPointData modelToIndex = (GeoPointData) getModelToResource(RESOURCE_PATH, GeoPointData.class);
		repository.save(modelToIndex);

		GeoDataQueryDTO geodataQuery = new GeoDataQueryDTO();
		geodataQuery.setSize(1);

		// Se elimina accessibilityIds ya que no está permitido para usuarios
		// básicos
		HashMap<String, Object> query = mapper.convertValue(geodataQuery, HashMap.class);
		query.remove("accessibilityIds");

		// @formatter:off

		this.mockMvc
				.perform(post(CITATIONS_BY_ACTIVITY_URL_BASE + "/_search").content(mapper.writeValueAsString(query))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// @formatter:on
	}
}
