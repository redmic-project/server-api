package es.redmic.api.maintenance.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.maintenance.device.model.DeviceType;
import es.redmic.db.maintenance.device.service.DeviceTypeService;
import es.redmic.es.maintenance.device.service.DeviceTypeESService;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.device.dto.DeviceTypeDTO;

@RestController
@RequestMapping(value = "${controller.mapping.DEVICE_TYPE}")
public class DeviceTypeController extends RWDomainController<DeviceType, DomainES, DeviceTypeDTO, SimpleQueryDTO> {

	@Autowired
	public DeviceTypeController(DeviceTypeService service, DeviceTypeESService serviceES) {
		super(service, serviceES);
	}
}