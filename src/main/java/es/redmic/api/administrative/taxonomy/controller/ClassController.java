package es.redmic.api.administrative.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.Classs;
import es.redmic.db.administrative.taxonomy.service.ClassService;
import es.redmic.es.administrative.taxonomy.service.ClassESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.CLASS}")
public class ClassController extends RWController<Classs, Taxon, TaxonDTO, MetadataQueryDTO> {

	@Autowired
	public ClassController(ClassService service, ClassESService serviceES) {
		super(service, serviceES);
	}
}