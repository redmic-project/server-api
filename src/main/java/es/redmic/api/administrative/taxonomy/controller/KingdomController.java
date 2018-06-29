package es.redmic.api.administrative.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.Kingdom;
import es.redmic.db.administrative.taxonomy.service.KingdomService;
import es.redmic.es.administrative.taxonomy.service.KingdomESService;
import es.redmic.models.es.administrative.taxonomy.dto.KingdomDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.KINGDOM}")
public class KingdomController extends RWController<Kingdom, Taxon, KingdomDTO, MetadataQueryDTO> {

	@Autowired
	public KingdomController(KingdomService service, KingdomESService serviceES) {
		super(service, serviceES);
	}
}