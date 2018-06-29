package es.redmic.api.maintenance.quality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RController;
import es.redmic.es.maintenance.quality.service.VFlagESService;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.quality.dto.VFlagDTO;
import es.redmic.models.es.maintenance.quality.model.VFlag;

@RestController
@RequestMapping(value = "${controller.mapping.VFLAG}")
public class VFlagController extends RController<VFlag, VFlagDTO, SimpleQueryDTO> {

	@Autowired
	public VFlagController(VFlagESService service) {
		super(service);
	}
}