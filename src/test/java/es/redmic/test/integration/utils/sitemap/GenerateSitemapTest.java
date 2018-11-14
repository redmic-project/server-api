package es.redmic.test.integration.utils.sitemap;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.reflect.Whitebox;
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
import es.redmic.api.utils.sitemap.dto.OpenModules;
import es.redmic.api.utils.sitemap.service.GenerateSitemapService;
import es.redmic.test.integration.ApiApplicationTest;
import es.redmic.test.integration.utils.JsonToBeanTestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ApiApplication.class,
		ApiApplicationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class GenerateSitemapTest {

	@MockBean
	GenerateSitemapController controller;

	@Autowired
	GenerateSitemapService service;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Autowired
	protected FilterChainProxy springSecurityFilterChain;

	protected MockMvc mockMvc;

	// @formatter:off

	private final String FILE_NAME = "sitemap.xml",
			GENERATE_SITEMAP_URL = "/" + FILE_NAME,
			OPEN_MODULES_RESOURCE = "/sitemap/openModules.json";

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

	@Test
	public void generateSitemap_CreateFile_IfThereAreOpenModules() throws Exception {

		File f = new File(DESTINATION_DIR + "/" + FILE_NAME);
		f.delete();

		OpenModules openModules = (OpenModules) JsonToBeanTestUtil.getBean(OPEN_MODULES_RESOURCE, OpenModules.class);

		Whitebox.setInternalState(service, "openModules", openModules);
		service.createSitemap();

		assertTrue(f.exists() && f.isFile());

		// TODO: Cuando tenga la base de datos de tests, comprobar contenido.
	}
}
