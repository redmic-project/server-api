package es.redmic.test.integration.utils.geo;

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
import es.redmic.testutils.oauth.IntegrationTestBase;

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
