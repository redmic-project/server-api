package es.redmic.api.atlas.layer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.atlas.layer.model.ThemeInspire;
import es.redmic.db.atlas.layer.service.ThemeInspireService;
import es.redmic.es.atlas.service.ThemeInspireESService;
import es.redmic.models.es.atlas.dto.ThemeInspireDTO;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.THEME_INSPIRE}")
public class ThemeInspireController extends
		RWController<ThemeInspire, es.redmic.models.es.atlas.model.ThemeInspire, ThemeInspireDTO, SimpleQueryDTO> {

	@Autowired
	public ThemeInspireController(ThemeInspireService service, ThemeInspireESService serviceES) {
		super(service, serviceES);
	}
}
