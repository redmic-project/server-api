package es.redmic.api.maintenance.administrative.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RController;
import es.redmic.es.maintenance.domain.administrative.service.ThemeInspireESService;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.ThemeInspireDTO;
import es.redmic.models.es.maintenance.administrative.model.ThemeInspire;

@RestController
@RequestMapping(value = "${controller.mapping.THEME_INSPIRE}")
public class ThemeInspireController extends RController<ThemeInspire, ThemeInspireDTO, SimpleQueryDTO> {

	public ThemeInspireController(ThemeInspireESService service) {
		super(service);
	}
}
