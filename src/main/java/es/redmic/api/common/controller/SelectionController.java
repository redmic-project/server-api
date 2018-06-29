package es.redmic.api.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import es.redmic.es.common.service.SelectionService;
import es.redmic.exception.databinding.DTONotValidException;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.JSONCollectionDTO;
import es.redmic.models.es.common.dto.SelectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.model.Selection;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

@RestController
@RequestMapping(value = "**${controller.mapping.SELECTIONS}")
public class SelectionController extends RBaseController<Selection, SelectionDTO, SimpleQueryDTO> implements ISettingsController {
	
	// TODO: Controlar las rutas que entran para que no puedan entrar todas si no queremos
	
	private SelectionService service;
	
	@Value("${controller.mapping.SELECTIONS}")
	String selectionBaseURI;

	@Autowired
	public SelectionController(SelectionService service) {
		super(service);
		this.service = service;

	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO saveSettings(@Valid @RequestBody SelectionDTO dto, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);
		
		String basePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		dto.setService(basePath.replace(selectionBaseURI + "/", ""));
		return new ElasticSearchDTO( service.save(dto), 1);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO updateSettings(@PathVariable("id") String id, @Valid @RequestBody SelectionDTO dto, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors())
			throw new DTONotValidException(bindingResult);
		
		String basePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		dto.setService(basePath.replace(selectionBaseURI + "/" + id, ""));
		return new ElasticSearchDTO(service.save(dto), 1);
	}

	@RequestMapping(value = "/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO findAllSettings(@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult, HttpServletRequest request) {
		
		processQuery(queryDTO, bindingResult);
		
		String basePath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		JSONCollectionDTO result = service.findAll(queryDTO, basePath.replace(selectionBaseURI + "/_search", ""));
		return new ElasticSearchDTO(result, result.getTotal());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO getSettings(@PathVariable("id") String id) {

		SelectionDTO response = service.get(id.toString());
		return new ElasticSearchDTO(response, response == null ? 0 : 1);
	}

	@RequestMapping(value = "/_suggest", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO suggestSettings(@RequestParam("fields") String[] fields, @RequestParam("text") String text,
			@RequestParam(required = false, value = "size") Integer size) {

		SimpleQueryDTO queryDTO = service.createSimpleQueryDTOFromSuggestQueryParams(fields, text, size);
		processQuery((SimpleQueryDTO) queryDTO);
		List<String> response = service.suggest(convertToQuery((SimpleQueryDTO) queryDTO));

		return new ElasticSearchDTO(response, response.size());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public SuperDTO deleteSettings(@PathVariable("id") String id) {

		service.delete(id);
		return new SuperDTO(true);
	}
}