package es.redmic.api.maintenance.quality.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RController;
import es.redmic.es.maintenance.quality.service.QFlagESService;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.quality.dto.QFlagDTO;
import es.redmic.models.es.maintenance.quality.model.QFlag;

@RestController
@RequestMapping(value = "${controller.mapping.QFLAG}")
public class QFlagController extends RController<QFlag, QFlagDTO, SimpleQueryDTO> {

	@Autowired
	public QFlagController(QFlagESService service) {
		super(service);
	}
}