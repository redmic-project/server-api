package es.redmic.api.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import es.redmic.models.es.common.dto.SelectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;

public interface ISettingsController {

	public SuperDTO findAllSettings(@Valid @RequestBody DataQueryDTO dto, BindingResult bindingResult,
			HttpServletRequest request);

	public SuperDTO getSettings(@PathVariable("id") String id);

	public SuperDTO suggestSettings(@RequestParam("fields") String[] fields, @RequestParam("text") String text,
			@RequestParam(required = false, value = "size") Integer size);

	public SuperDTO saveSettings(@Valid @RequestBody SelectionDTO dto, BindingResult bindingResult,
			HttpServletRequest request);

	public SuperDTO updateSettings(@PathVariable("id") String id, @Valid @RequestBody SelectionDTO dto,
			BindingResult bindingResult, HttpServletRequest request);

	public SuperDTO deleteSettings(@PathVariable("id") String id);
}
