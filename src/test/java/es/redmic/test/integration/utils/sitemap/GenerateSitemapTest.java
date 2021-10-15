package es.redmic.test.integration.utils.sitemap;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import es.redmic.ApiApplication;
import es.redmic.api.utils.sitemap.controller.GenerateSitemapController;
import es.redmic.test.integration.ApiApplicationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ApiApplication.class,
		ApiApplicationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GenerateSitemapTest {

	@MockBean
	GenerateSitemapController controller;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Autowired
	protected FilterChainProxy springSecurityFilterChain;

	protected MockMvc mockMvc;

	// @formatter:off

	private final String FILE_NAME = "sitemap.xml",
			GENERATE_SITEMAP_URL = "/" + FILE_NAME;

	// @formatter:on

	@Value("${property.SITEMAP_DESTINATION_DIR}")
	private String DESTINATION_DIR;

	@Before
	public void setUp() {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(springSecurityFilterChain)
				.build();
	}

	@Test
	public void getSitemap_ReturnFile_IfSitemapWasGenerated() throws Exception {

		mockMvc.perform(get(GENERATE_SITEMAP_URL)).andExpect(status().isOk());
	}
}
