package es.redmic.api.utils.sitemap.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.redmic.api.utils.sitemap.service.GenerateSitemapService;
import es.redmic.exception.mediastorage.MSFileNotFoundException;
import es.redmic.mediastorage.service.MediaStorageServiceItfc;

@Controller
public class GenerateSitemapController {

	private GenerateSitemapService service;

	@Autowired
	MediaStorageServiceItfc mediaStorageService;

	@Value("${property.SITEMAP_DESTINATION_DIR}")
	private String PATH;

	@Autowired
	public GenerateSitemapController(GenerateSitemapService service) {
		this.service = service;
	}

	@PostConstruct
	public void GenerateSitemapControllerPostConstruct() {
		service.createSitemap();
	}

	@RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET, produces = { "text/xml", "application/xml" })
	public void download(HttpServletResponse response) {

		String name = "sitemap.xml";

		// TODO: Esta funcionalidad es provisional. En caso de mantener el servir el xml
		// desde api, añadir esta funcionalidad al servicio.
		File file = mediaStorageService.openTempFile(PATH, name);

		response.reset();
		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "inline;filename=\"" + name + "\"");

		try {

			IOUtils.copy(new FileInputStream(file), response.getOutputStream());
		} catch (Exception e) {
			throw new MSFileNotFoundException(file.getName(), PATH, e);
		}
	}
}
