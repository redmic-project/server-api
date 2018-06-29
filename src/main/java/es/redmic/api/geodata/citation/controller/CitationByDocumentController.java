package es.redmic.api.geodata.citation.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RBaseController;
import es.redmic.es.geodata.citation.service.CitationESService;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.GeoDataQueryDTO;
import es.redmic.models.es.geojson.citation.dto.CitationDTO;
import es.redmic.models.es.geojson.common.model.GeoPointData;

@RestController
@RequestMapping(value = "${controller.mapping.CITATIONS_BY_DOCUMENTS}")
public class CitationByDocumentController extends RBaseController<GeoPointData, CitationDTO, GeoDataQueryDTO> {

	private CitationESService serviceES;

	@Autowired
	public CitationByDocumentController(CitationESService serviceES) {
		super(serviceES);
		this.serviceES = serviceES;
	}
	
	@RequestMapping(value = "/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO getCitations(@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult, @PathVariable("documentId") String documentId) {
		
		processQuery(queryDTO, bindingResult);

		return new ElasticSearchDTO(serviceES.findByDocument(queryDTO, documentId));
	}
}