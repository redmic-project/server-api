package es.redmic.api.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import es.redmic.es.common.service.SelectionWorkService;
import es.redmic.es.data.common.service.RDataESService;
import es.redmic.models.es.common.dto.BaseDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SelectionWorkDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.model.BaseES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

public abstract class SelectionWorkController<TModel extends BaseES<?>, TDTO extends BaseDTO<?>, TQueryDTO extends SimpleQueryDTO>
		extends RBaseController<TModel, TDTO, TQueryDTO> implements ISelectionWorkController {

	@Autowired
	SelectionWorkService selectionWorkService;

	private RDataESService<TModel, TDTO> service;

	public SelectionWorkController(RDataESService<TModel, TDTO> service) {
		super(service);
		this.service = service;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "${controller.mapping.SELECTION_WORK}", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO saveSelectionWork(@Valid @RequestBody SelectionWorkDTO dto, BindingResult bindingResult,
			HttpServletRequest request) {

		processQuery((TQueryDTO) dto.getQuery(), bindingResult);
	
		dto.setService(getServiceName(request));

		SelectionWorkDTO out = selectionWorkService.save(dto, service);
		if (out != null && out.getIds() != null)
			return new ElasticSearchDTO(out, out.getIds().size());
		else
			return new ElasticSearchDTO(out, 0);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "${controller.mapping.SELECTION_WORK}/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO updateSelection(@Valid @RequestBody SelectionWorkDTO dto, BindingResult bindingResult,
			HttpServletRequest request) {

		processQuery((TQueryDTO) dto.getQuery(), bindingResult);

		dto.setService(getServiceName(request));

		SelectionWorkDTO out = selectionWorkService.update(dto, service);
		if (out != null && out.getIds() != null)
			return new ElasticSearchDTO(out, out.getIds().size());
		else
			return new ElasticSearchDTO(out, 0);
	}

	@RequestMapping(value = "${controller.mapping.SELECTION_WORK}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO getSelectionWork(@PathVariable(value = "id") String id) {

		SelectionWorkDTO out = selectionWorkService.get(id);
		if (out != null && out.getIds() != null)
			return new ElasticSearchDTO(out, out.getIds().size());
		else
			return new ElasticSearchDTO(out, 0);
	}

	private String getServiceName(HttpServletRequest request) {
		
		String[] pathSplit = getPathSplit(request);

		return pathSplit[1];
	}
	
	private String[] getPathSplit(HttpServletRequest request) {
		
		String restOfTheUrl = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		return restOfTheUrl.split("/");
	}
}
