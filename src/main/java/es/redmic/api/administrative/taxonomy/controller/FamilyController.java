package es.redmic.api.administrative.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.Family;
import es.redmic.db.administrative.taxonomy.service.FamilyService;
import es.redmic.es.administrative.taxonomy.service.FamilyESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.FAMILY}")
public class FamilyController extends RWController<Family, Taxon, TaxonDTO, MetadataQueryDTO> {

	@Autowired
	public FamilyController(FamilyService service, FamilyESService serviceES) {
		super(service, serviceES);
	}
}