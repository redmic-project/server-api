package es.redmic.test.integration.administrative;

/*-
 * #%L
 * API
 * %%
 * Copyright (C) 2019 - 2022 REDMIC Project / Server
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.redmic.ApiApplication;
import es.redmic.es.administrative.repository.DocumentESRepository;
import es.redmic.models.es.administrative.model.Document;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.test.integration.common.IntegrationTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ApiApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DocumentControllerTest extends IntegrationTestBase {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	@Value("${controller.mapping.DOCUMENT}")
	private String CONTROLLER_DOCUMENT;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	DocumentESRepository repository;

	private RestDocumentationResultHandler document;

	Document modelToIndex;

	@Override
	@Before
	public void setUp() {

		// @formatter:off
		this.document = document("{class-name}/{method-name}", preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()));

		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain)
				.apply(documentationConfiguration(this.restDocumentation)).alwaysDo(this.document).build();

		modelToIndex = new Document();
		modelToIndex.setId(33L);
		modelToIndex.setCode("ext-3");
		modelToIndex.setYear("2000");
		modelToIndex.setAuthor("Fulanito");
		modelToIndex.setLanguage("es");
		modelToIndex.setTitle("Sólo para administradores");
		modelToIndex.setSource("Esto es un documento en español");
		modelToIndex.setInternalUrl("/api/mediastorage/123.pdf");
		modelToIndex.setPrivateInternalUrl(true);

		DomainES documentType = new DomainES();
		documentType.setId(1L);
		documentType.setName("Artículo de revista");
		documentType.setName_en("...");
		modelToIndex.setDocumentType(documentType);

		modelToIndex = repository.save(modelToIndex);

		// @formatter:on
	}

	@Test
	public void checkDocumentController_ReturnInternalUrl_IfSearchDocumentAsAdminUser() throws Exception {

		MetadataQueryDTO query = new MetadataQueryDTO();
		query.setSize(1);

		String content = mapper.writeValueAsString(query);

		ResultActions result = this.mockMvc
			.perform(post(CONTROLLER_DOCUMENT + "/_search").content(content)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getTokenAdministratorUser()));

		result.andExpect(status().is2xxSuccessful());
		result.andExpect(jsonPath("$.body.data[0].internalUrl", notNullValue()));
	}

	@Test
	public void checkDocumentController_NoReturnInternalUrl_IfSearchDocumentAsUserAndPrivateInternalUrlIsTrue() throws Exception {

		MetadataQueryDTO query = new MetadataQueryDTO();
		query.setSize(1);

		ResultActions result = this.mockMvc
			.perform(post(CONTROLLER_DOCUMENT + "/_search").content(mapper.writeValueAsString(query))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getTokenUser()));

		result.andExpect(status().is2xxSuccessful());
		result.andExpect(jsonPath("$.body.data[0].internalUrl", nullValue()));
	}

	@Test
	public void checkDocumentController_ReturnInternalUrl_IfSearchDocumentAsUserAndPrivateInternalUrlIsFalse() throws Exception {

		MetadataQueryDTO query = new MetadataQueryDTO();
		query.setSize(1);

		modelToIndex.setPrivateInternalUrl(false);
		modelToIndex = repository.save(modelToIndex);

		ResultActions result = this.mockMvc
			.perform(post(CONTROLLER_DOCUMENT + "/_search").content(mapper.writeValueAsString(query))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header("Authorization", "Bearer " + getTokenUser()));

		result.andExpect(status().is2xxSuccessful());
		result.andExpect(jsonPath("$.body.data[0].internalUrl", notNullValue()));
	}
}
