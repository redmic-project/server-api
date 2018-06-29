package es.redmic.api.maintenance.objects.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.maintenance.objects.model.ObjectType;
import es.redmic.db.maintenance.objects.service.ObjectTypeService;
import es.redmic.es.maintenance.objects.service.ObjectTypeESService;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.objects.dto.ObjectTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.OBJECT_TYPE}")
public class ObjectTypeController extends
		RWController<ObjectType, es.redmic.models.es.maintenance.objects.model.ObjectType, ObjectTypeDTO, MetadataQueryDTO> {

	@Autowired
	public ObjectTypeController(ObjectTypeService service, ObjectTypeESService serviceES) {
		super(service, serviceES);
	}
}
