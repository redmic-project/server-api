package es.redmic.api.administrative.taxonomy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.Animal;
import es.redmic.db.administrative.taxonomy.service.AnimalService;
import es.redmic.es.administrative.taxonomy.service.AnimalESService;
import es.redmic.models.es.administrative.taxonomy.dto.AnimalDTO;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ANIMAL}")
public class AnimalController extends RWController<Animal, es.redmic.models.es.administrative.taxonomy.model.Animal, AnimalDTO, MetadataQueryDTO> {

	@Autowired
	public AnimalController(AnimalService service, AnimalESService serviceES) {
		super(service, serviceES);
	}
}