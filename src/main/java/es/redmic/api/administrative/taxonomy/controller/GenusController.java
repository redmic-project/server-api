package es.redmic.api.administrative.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.Genus;
import es.redmic.db.administrative.taxonomy.service.GenusService;
import es.redmic.es.administrative.taxonomy.service.GenusESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.GENUS}")
public class GenusController extends RWController<Genus, Taxon, TaxonDTO, MetadataQueryDTO> {

	@Autowired
	public GenusController(GenusService service, GenusESService serviceES) {
		super(service, serviceES);
	}
}
