package es.redmic.test.integration.administrative;

import static org.junit.Assert.assertEquals;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import es.redmic.ApiApplication;
import es.redmic.es.administrative.repository.ActivityESRepository;
import es.redmic.models.es.administrative.model.Activity;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.test.integration.ApiApplicationTest;
import es.redmic.test.integration.common.IntegrationTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ApiApplication.class,
		ApiApplicationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ActivityControllerTest extends IntegrationTestBase {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	@Autowired
	ObjectMapper mapper;

	@Autowired
	ActivityESRepository repository;

	private RestDocumentationResultHandler document;

	private final String ACTIVITIES_URL_BASE = "/activities";

	@Override
	@Before
	public void setUp() {

		// @formatter:off

		this.document = document("{class-name}/{method-name}", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()));

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain)
				.apply(documentationConfiguration(this.restDocumentation)).alwaysDo(this.document).build();

		Activity modelToIndex = new Activity();
		modelToIndex.setId(3L);
		modelToIndex.setName("name");

		DomainES accessibility = new DomainES();
		accessibility.setId(1L);
		accessibility.setName("Libre");
		accessibility.setName_en("Free");
		modelToIndex.setAccessibility(accessibility);

		DomainES rank = new DomainES();
		rank.setId(3L);
		rank.setName("Actividad");
		rank.setName_en("Activity");
		modelToIndex.setRank(rank);

		modelToIndex = repository.save(modelToIndex);

		// @formatter:on
	}

	@Test
	public void deleteActivity_Return401_WhenUserIsAnonymous() throws Exception {

		this.mockMvc.perform(delete(ACTIVITIES_URL_BASE + "/1")).andExpect(status().isUnauthorized());
	}

	@Test
	public void searchActivities_Return400_WhenSearchByNotAllowedProperties() throws Exception {

		DataQueryDTO dataQuery = new DataQueryDTO();
		dataQuery.setSize(1);
		dataQuery.setAccessibilityIds(Arrays.asList(1L));

		// @formatter:off

		this.mockMvc
				.perform(post(ACTIVITIES_URL_BASE + "/_search").content(mapper.writeValueAsString(dataQuery))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());

		// @formatter:on
	}

	@SuppressWarnings("unchecked")
	@Test
	public void searchActivities_Return200_WhenSearchIsCorrect() throws Exception {

		DataQueryDTO dataQuery = new DataQueryDTO();
		dataQuery.setSize(1);

		// Se elimina accessibilityIds ya que no está permitido para usuarios
		// básicos
		HashMap<String, Object> query = mapper.convertValue(dataQuery, HashMap.class);
		query.remove("accessibilityIds");

		// @formatter:off

		this.mockMvc
				.perform(post(ACTIVITIES_URL_BASE + "/_search").content(mapper.writeValueAsString(query))
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		// @formatter:on
	}

	@Test
	public void getActivities_Return200_WhenSearchIsCorrect() throws Exception {

		// @formatter:off

		this.mockMvc
			.perform(get(ACTIVITIES_URL_BASE)
				.param("from", "0")
				.param("size", "10")
				.param("returnFields", "id")
				.param("returnFields", "name")
					.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.body.data", notNullValue()))
			.andExpect(jsonPath("$.body.data[0]", notNullValue()))
			.andExpect(jsonPath("$.body.data[0].id", is(3)))
			.andExpect(jsonPath("$.body.data[0].name", is("name")))
			.andExpect(jsonPath("$.body.data.length()", is(1)));

		// @formatter:on
	}
}
