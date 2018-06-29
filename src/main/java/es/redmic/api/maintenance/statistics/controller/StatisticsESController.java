package es.redmic.api.maintenance.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.es.maintenance.statistics.service.StatisticsESService;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.maintenance.statistics.dto.AdministrativeStatisticsDTO;

@RestController
@RequestMapping(value = "${controller.mapping.STATISTICS}")
public class StatisticsESController {

	@Autowired
	StatisticsESService service;

	@RequestMapping(value = "/administrative", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO projectsStatistics() {

		AdministrativeStatisticsDTO response = service.administrativeStatistics();

		return new BodyItemDTO<AdministrativeStatisticsDTO>(response);
	}
}
