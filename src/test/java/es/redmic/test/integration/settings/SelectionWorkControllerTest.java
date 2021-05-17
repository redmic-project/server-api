package es.redmic.test.integration.settings;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.redmic.ApiApplication;
import es.redmic.models.es.common.dto.SelectionWorkDTO;
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

	SelectionWorkDTO selectionWork;

	@Before
	public void initialize() {

		selectionWork = new SelectionWorkDTO();
		selectionWork.setAction("select");
		List<String> selectionWorkIds = new ArrayList<>();
		selectionWorkIds.add("520");
		selectionWork.setIds(selectionWorkIds);
	}

	@Test
	public void saveSelectionWork_Return200_WhenRequestIsCorrect() throws Exception {

		// @formatter:off

		this.mockMvc
			.perform(post(ACTIVITIES_SELECTION_WORK_URL).content(mapper.writeValueAsString(selectionWork))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		// @formatter:on
	}
}
