package es.redmic.test.integration.utils.geo;

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

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;

import es.redmic.ApiApplication;
import es.redmic.test.integration.ApiApplicationTest;
import es.redmic.test.integration.common.IntegrationTestBase;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ApiApplication.class,
		ApiApplicationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ConvertShapefileToGeoJSONTest extends IntegrationTestBase {

	@Value("${controller.mapping.utils.geo.CONVERT_TO_GEOJSON}")
	String URL;

	@Test
	public void readString() throws Exception {

		InputStream resource = getClass().getResourceAsStream("/geo/shapefile/Point.shp");
		MockMultipartFile file = new MockMultipartFile("file", "Point.shp", "application/octet-stream", resource);

		// @formatter:off

		MvcResult result = mockMvc.perform(fileUpload(URL).file(file)).andExpect(status().isOk()).andReturn();

		// @formatter:on

		String content = result.getResponse().getContentAsString();

		assertTrue(content.contains("\"type\":\"FeatureCollection\""));
		assertTrue(content.contains("\"type\":\"Feature\""));
		assertTrue(content.contains("\"geometry\":{\"type\":\"Point\""));
	}
}
