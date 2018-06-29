package es.redmic.api.administrative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.model.Platform;
import es.redmic.db.administrative.service.PlatformService;
import es.redmic.es.administrative.service.PlatformESService;
import es.redmic.models.es.administrative.dto.PlatformDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.PLATFORM}")
public class PlatformController extends
		RWController<Platform, es.redmic.models.es.administrative.model.Platform, PlatformDTO, MetadataQueryDTO> {

	PlatformESService serviceES;

	@Autowired
	public PlatformController(PlatformService service, PlatformESService serviceES) {
		super(service, serviceES);
		this.serviceES = serviceES;
	}

	@RequestMapping(value = "${contoller.mapping.FILTERED_ACTIVITIES}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO getActivities(@RequestParam(required = false, value = "fields") String[] fields,
			@RequestParam(required = false, value = "text") String text,
			@RequestParam(required = false, value = "from") Integer from,
			@RequestParam(required = false, value = "size") Integer size, @PathVariable("id") Long id) {

		MetadataQueryDTO queryDTO = objectMapper.convertValue(
				ESService.createSimpleQueryDTOFromTextQueryParams(fields, text, from, size), MetadataQueryDTO.class);
		processQuery(queryDTO);

		return new ElasticSearchDTO(serviceES.getActivities(convertToQuery(queryDTO), id));
	}
}
