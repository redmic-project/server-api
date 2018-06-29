package es.redmic.api.common.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.service.ReportFileService;

@RestController
@RequestMapping(value = "${controller.mapping.REPORT}")
public class ReportFileController {

	@Autowired
	ReportFileService service;

	public ReportFileController() {
	}

	@RequestMapping(value = "/{name:.+}", method = RequestMethod.GET, produces = { "text/*", "application/*" })
	public void download(@PathVariable("name") String name, HttpServletResponse response) {
		// TODO: controlar que el usuario que lo abre es quien lo gener� ?
		service.returnReportFile(name, response);
	}
}
