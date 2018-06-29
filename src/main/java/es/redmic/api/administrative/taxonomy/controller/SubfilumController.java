package es.redmic.api.administrative.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.Subfilum;
import es.redmic.db.administrative.taxonomy.service.SubfilumService;
import es.redmic.es.administrative.taxonomy.service.SubfilumESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SUBFILUM}")
public class SubfilumController extends RWController<Subfilum, Taxon, TaxonDTO, MetadataQueryDTO> {

	@Autowired
	public SubfilumController(SubfilumService service, SubfilumESService serviceES) {
		super(service, serviceES);
	}
}
