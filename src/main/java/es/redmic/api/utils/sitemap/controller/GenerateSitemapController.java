package es.redmic.api.utils.sitemap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.redmic.api.utils.sitemap.service.GenerateSitemapService;
import es.redmic.exception.dto.SuperDTO;

@Controller
public class GenerateSitemapController {

	private GenerateSitemapService service;

	@Autowired
	public GenerateSitemapController(GenerateSitemapService service) {
		this.service = service;
	}

	@RequestMapping(value = "/generate-sitemap", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO getMapping() {

		service.createSitemap();

		return new SuperDTO(true);
	}
}
