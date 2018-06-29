package es.redmic.api.administrative.taxonomy.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.Misidentification;
import es.redmic.db.administrative.taxonomy.service.MisidentificationService;
import es.redmic.es.administrative.service.ActivityESService;
import es.redmic.es.administrative.taxonomy.service.MisidentificationESService;
import es.redmic.models.es.administrative.taxonomy.dto.MisidentificationDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;

@RestController
@RequestMapping(value = "${controller.mapping.MISIDENTIFICATION}")
public class MisidentificationController extends
		RWController<Misidentification, es.redmic.models.es.administrative.taxonomy.model.Misidentification, MisidentificationDTO, MetadataQueryDTO> {

	@Autowired
	ActivityESService activityService;

	@Autowired
	public MisidentificationController(MisidentificationService service, MisidentificationESService serviceES) {
		super(service, serviceES);
	}

	@SuppressWarnings("serial")
	@RequestMapping(value = "/{id}/documents/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO getDocuments(@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult,
			@PathVariable("id") Long id) {

		queryDTO.addTerm("misidentification", id);
		queryDTO.setReturnFields(new ArrayList<String>() {
			{
				add("documents");
			}
		});

		processQuery(queryDTO, bindingResult);
		
		return new ElasticSearchDTO(activityService.find(queryDTO));
	}
}