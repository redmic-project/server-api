package es.redmic.api.maintenance.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.administrative.model.DocumentType;
import es.redmic.db.maintenance.administrative.service.DocumentTypeService;
import es.redmic.es.maintenance.domain.administrative.service.DocumentTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.DocumentTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.DOCUMENT_TYPE}")
public class DocumentTypeController
		extends RWDomainController<DocumentType, DomainES, DocumentTypeDTO, SimpleQueryDTO> {

	@Autowired
	public DocumentTypeController(DocumentTypeService service, DocumentTypeESService serviceES) {
		super(service, serviceES);
	}
}