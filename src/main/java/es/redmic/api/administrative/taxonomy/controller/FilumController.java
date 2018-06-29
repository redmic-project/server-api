package es.redmic.api.administrative.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.Filum;
import es.redmic.db.administrative.taxonomy.service.FilumService;
import es.redmic.es.administrative.taxonomy.service.FilumESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.FILUM}")
public class FilumController extends RWController<Filum, Taxon, TaxonDTO, MetadataQueryDTO> {

	@Autowired
	public FilumController(FilumService service, FilumESService serviceES) {
		super(service, serviceES);
	}
}
