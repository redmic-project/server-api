package es.redmic.api.maintenance.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.maintenance.common.controller.RWDomainController;
import es.redmic.db.administrative.model.Country;
import es.redmic.db.administrative.service.CountryService;
import es.redmic.es.maintenance.domain.administrative.service.CountryESService;
import es.redmic.models.es.common.query.dto.SimpleQueryDTO;
import es.redmic.models.es.maintenance.administrative.dto.CountryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.COUNTRIES}")
public class CountryController
		extends RWDomainController<Country, es.redmic.models.es.maintenance.administrative.model.Country, CountryDTO, SimpleQueryDTO> {

	@Autowired
	public CountryController(CountryService service, CountryESService serviceES) {
		super(service, serviceES);
	}
}