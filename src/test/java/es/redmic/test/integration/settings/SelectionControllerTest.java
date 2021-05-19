package es.redmic.test.integration.settings;

/*-
 * #%L
 * API
 * %%
 * Copyright (C) 2019 - 2021 REDMIC Project / Server
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import es.redmic.ApiApplication;
import es.redmic.es.common.repository.SelectionWorkRepository;
import es.redmic.models.es.common.dto.SelectionDTO;
import es.redmic.models.es.common.dto.SelectionWorkDTO;
import es.redmic.models.es.common.model.Selection;
import es.redmic.test.integration.ApiApplicationTest;
import es.redmic.test.integration.common.IntegrationTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ApiApplication.class,
		ApiApplicationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SelectionControllerTest extends IntegrationTestBase {

	private final String ACTIVITIES_SELECTION_URL = "/activities/_selections/";

	@Autowired
	ObjectMapper mapper;

	@Autowired
	SelectionWorkRepository repository;

	@Test
	public void saveSelection_Return401_WhenUserIsAnonymous() throws Exception {

		// @formatter:off

		SelectionDTO selection;
		selection = new SelectionDTO();
		selection.setName("prueba");

		this.mockMvc
			.perform(post(ACTIVITIES_SELECTION_URL).content(mapper.writeValueAsString(selection))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());

		// @formatter:on
	}

	@Test
	public void saveSelection_Return200_WhenRequestIsCorrect() throws Exception {

		// @formatter:off

		SelectionWorkDTO selectionWork = new SelectionWorkDTO();
		selectionWork.setAction("select");

		List<String> selectionWorkIds = new ArrayList<>();
		selectionWorkIds.add("520");
		selectionWork.setIds(selectionWorkIds);

		String id = UUID.randomUUID().toString();

		Selection modelToIndex = new Selection();
		modelToIndex.setDate(DateTime.now());
		modelToIndex.setId(id);
		modelToIndex.setIds(selectionWorkIds);
		modelToIndex.setService("activities");

		repository.save(modelToIndex);

		SelectionDTO selection;
		selection = new SelectionDTO();
		selection.setName("prueba");

		List<String> ids = new ArrayList<>();
		ids.add(id);
		selection.setIds(ids);

		this.mockMvc
			.perform(post(ACTIVITIES_SELECTION_URL).content(mapper.writeValueAsString(selection))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + getTokenUser()))
			.andExpect(status().isOk());

		// @formatter:on
	}
}
