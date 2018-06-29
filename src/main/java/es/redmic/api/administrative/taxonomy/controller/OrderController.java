package es.redmic.api.administrative.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.Orderr;
import es.redmic.db.administrative.taxonomy.service.OrderService;
import es.redmic.es.administrative.taxonomy.service.OrderESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.model.Taxon;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ORDER}")
public class OrderController extends RWController<Orderr, Taxon, TaxonDTO, MetadataQueryDTO> {

	@Autowired
	public OrderController(OrderService service, OrderESService serviceES) {
		super(service, serviceES);
	}
}
