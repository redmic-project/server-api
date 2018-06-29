package es.redmic.api.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import es.redmic.models.es.common.dto.SelectionWorkDTO;
import es.redmic.models.es.common.dto.SuperDTO;


public interface ISelectionWorkController {
	
	public SuperDTO getSelectionWork(@PathVariable(value = "id") String id);
	public SuperDTO saveSelectionWork(@RequestBody SelectionWorkDTO dto, BindingResult bindingResult, HttpServletRequest request);
	public SuperDTO updateSelection(@RequestBody SelectionWorkDTO dto, BindingResult bindingResult, HttpServletRequest request);
}
