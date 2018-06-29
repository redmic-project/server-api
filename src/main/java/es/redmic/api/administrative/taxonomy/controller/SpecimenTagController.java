package es.redmic.api.administrative.taxonomy.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.service.JsonSchemaService;
import es.redmic.models.es.administrative.taxonomy.dto.SpecimenTagDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SPECIMENTAG}")
public class SpecimenTagController {
	
	@Autowired
	JsonSchemaService service;

	@RequestMapping(value = "${controller.mapping.EDIT_SCHEMA}", method = RequestMethod.GET)
	@ResponseBody
	public HashMap<String,Object> getJsonSchema(HttpServletResponse response) {
		return service.getJsonSchema(SpecimenTagDTO.class.getName());
	}
}
