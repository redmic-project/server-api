package es.redmic.api.maintenance.areas.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.service.JsonSchemaService;
import es.redmic.models.es.maintenance.areas.dto.AreaClassificationDTO;

@RestController
@RequestMapping(value = "${controller.mapping.AREA_CLASSIFICATION}")
public class AreaClassificationController {

	@Autowired
	JsonSchemaService service;

	@RequestMapping(value = "${controller.mapping.EDIT_SCHEMA}", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String, Object> getJsonSchema(HttpServletResponse response) {
		return service.getJsonSchema(AreaClassificationDTO.class.getName());
	}
}
