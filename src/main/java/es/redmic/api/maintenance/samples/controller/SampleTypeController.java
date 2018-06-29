package es.redmic.api.maintenance.samples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.samples.model.SampleType;
import es.redmic.db.maintenance.samples.service.SampleTypeService;
import es.redmic.es.maintenance.samples.service.SampleTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.samples.dto.SampleTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.SAMPLE_TYPE}")
public class SampleTypeController extends RWDomainController<SampleType, DomainES, SampleTypeDTO, SimpleQueryDTO> {

	@Autowired
	public SampleTypeController(SampleTypeService service, SampleTypeESService serviceES) {
		super(service, serviceES);
	}
}