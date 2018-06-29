package es.redmic.api.maintenance.device.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.maintenance.device.model.Device;
import es.redmic.db.maintenance.device.service.DeviceService;
import es.redmic.es.maintenance.device.service.DeviceESService;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.maintenance.device.dto.DeviceDTO;

@RestController
@RequestMapping(value = "${controller.mapping.DEVICE}")
public class DeviceController
		extends RWController<Device, es.redmic.models.es.maintenance.device.model.Device, DeviceDTO, MetadataQueryDTO> {

	@Autowired
	public DeviceController(DeviceService service, DeviceESService serviceES) {
		super(service, serviceES);
	}
}