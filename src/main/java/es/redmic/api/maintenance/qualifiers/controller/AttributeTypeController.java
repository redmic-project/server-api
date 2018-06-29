package es.redmic.api.maintenance.qualifiers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.maintenance.qualifiers.model.AttributeType;
import es.redmic.db.maintenance.qualifiers.service.AttributeTypeService;
import es.redmic.es.maintenance.qualifiers.service.AttributeTypeESService;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.qualifiers.dto.AttributeTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.ATTRIBUTE_TYPE}")
public class AttributeTypeController extends
		RWController<AttributeType, es.redmic.models.es.maintenance.qualifiers.model.AttributeType, AttributeTypeDTO, MetadataQueryDTO> {

	@Autowired
	public AttributeTypeController(AttributeTypeService service, AttributeTypeESService serviceES) {
		super(service, serviceES);
	}
}