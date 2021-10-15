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

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import es.redmic.ApiApplication;
import es.redmic.es.common.repository.SelectionWorkRepository;
import es.redmic.models.es.common.dto.SelectionWorkDTO;
import es.redmic.models.es.common.model.Selection;
import es.redmic.test.integration.ApiApplicationTest;
import es.redmic.test.integration.common.IntegrationTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ApiApplication.class,
		ApiApplicationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class SelectionWorkControllerTest extends IntegrationTestBase {

	private final String ACTIVITIES_SELECTION_WORK_URL = "/activities/_selection/";

	@Autowired
	ObjectMapper mapper;

	@Autowired
	SelectionWorkRepository repository;

	@Test
	public void saveNewSelectionWork_Return200_WhenRequestIsCorrect() throws Exception {

		// @formatter:off

		SelectionWorkDTO selectionWork;
		selectionWork = new SelectionWorkDTO();
		selectionWork.setAction("select");
		List<String> selectionWorkIds = new ArrayList<>();
		selectionWorkIds.add("520");
		selectionWork.setIds(selectionWorkIds);

		this.mockMvc
			.perform(post(ACTIVITIES_SELECTION_WORK_URL).content(mapper.writeValueAsString(selectionWork))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success", is(true)))
			.andExpect(jsonPath("$.body", notNullValue()))
			.andExpect(jsonPath("$.body.ids", notNullValue()))
			.andExpect(jsonPath("$.body.ids.length()", is(1)))
			.andExpect(jsonPath("$.body.ids[0]", is("520")));

		// @formatter:on
	}

	@Test
	public void selectInSelectionWork_Return200_WhenRequestIsCorrect() throws Exception {

		// @formatter:off

		SelectionWorkDTO selectionWork;
		selectionWork = new SelectionWorkDTO();
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

		selectionWork.getIds().clear();
		selectionWork.getIds().add(("521"));

		selectionWork.setId(id);

		this.mockMvc
			.perform(put(ACTIVITIES_SELECTION_WORK_URL + id).content(mapper.writeValueAsString(selectionWork))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success", is(true)))
			.andExpect(jsonPath("$.body", notNullValue()))
			.andExpect(jsonPath("$.body.ids", notNullValue()))
			.andExpect(jsonPath("$.body.ids.length()", is(2)))
			.andExpect(jsonPath("$.body.ids[0]", is("520")))
			.andExpect(jsonPath("$.body.ids[1]", is("521")));

		// @formatter:on
	}

	@Test
	public void deselectInSelectionWork_Return200_WhenRequestIsCorrect() throws Exception {

		// @formatter:off

		SelectionWorkDTO selectionWork;
		selectionWork = new SelectionWorkDTO();
		selectionWork.setAction("deselect");
		List<String> selectionWorkIds = new ArrayList<>();
		selectionWorkIds.add("521");
		selectionWork.setIds(selectionWorkIds);

		String id = UUID.randomUUID().toString();

		Selection modelToIndex = new Selection();
		modelToIndex.setDate(DateTime.now());
		modelToIndex.setId(id);
		modelToIndex.setIds(selectionWorkIds);
		modelToIndex.setService("activities");

		repository.save(modelToIndex);

		selectionWork.setId(id);

		this.mockMvc
			.perform(put(ACTIVITIES_SELECTION_WORK_URL + id).content(mapper.writeValueAsString(selectionWork))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.body", notNullValue()))
			.andExpect(jsonPath("$.body.ids", notNullValue()))
			.andExpect(jsonPath("$.body.ids.length()", is(1))) // devuelve el mismo item
			.andExpect(jsonPath("$.body.action", is("deselect")));

		this.mockMvc
			.perform(get(ACTIVITIES_SELECTION_WORK_URL + id)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.body", notNullValue()))
			.andExpect(jsonPath("$.body.ids", notNullValue()))
			.andExpect(jsonPath("$.body.ids.length()", is(0)));
		// @formatter:on
	}


	@Test
	public void clearInSelectionWork_Return200_WhenRequestIsCorrect() throws Exception {

		// @formatter:off

		SelectionWorkDTO selectionWork;
		selectionWork = new SelectionWorkDTO();
		selectionWork.setAction("clear");

		String id = UUID.randomUUID().toString();

		Selection modelToIndex = new Selection();
		modelToIndex.setDate(DateTime.now());
		modelToIndex.setId(id);
		List<String> selectionWorkIds = new ArrayList<>();
		selectionWorkIds.add("521");
		modelToIndex.setIds(selectionWorkIds);
		modelToIndex.setService("activities");

		repository.save(modelToIndex);

		selectionWork.setId(id);

		this.mockMvc
			.perform(put(ACTIVITIES_SELECTION_WORK_URL + id).content(mapper.writeValueAsString(selectionWork))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.body", notNullValue()))
			.andExpect(jsonPath("$.body.ids", notNullValue()))
			.andExpect(jsonPath("$.body.ids.length()", is(0)));

		// @formatter:on
	}

	// TODO: hacer tests de selectAll y reverse (es necesario guardar actividades para ello)
}
