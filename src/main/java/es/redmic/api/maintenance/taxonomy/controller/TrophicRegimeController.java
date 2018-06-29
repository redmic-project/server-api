package es.redmic.api.maintenance.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.taxonomy.model.TrophicRegime;
import es.redmic.db.maintenance.taxonomy.service.TrophicRegimeService;
import es.redmic.es.maintenance.domain.administrative.taxonomy.service.TrophicRegimeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.taxonomy.dto.TrophicRegimeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.TROPHIC_REGIME}")
public class TrophicRegimeController extends RWDomainController<TrophicRegime, DomainES, TrophicRegimeDTO, SimpleQueryDTO> {

	@Autowired
	public TrophicRegimeController(TrophicRegimeService service, TrophicRegimeESService serviceES) {
		super(service, serviceES);
	}
}