package es.redmic.api.administrative.taxonomy.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.common.controller.RWController;
import es.redmic.db.administrative.taxonomy.model.Taxon;
import es.redmic.db.common.service.IServiceRW;
import es.redmic.es.administrative.taxonomy.service.TaxonESService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.common.dto.ElasticSearchDTO;
import es.redmic.models.es.common.dto.JSONCollectionDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.common.query.dto.MetadataQueryDTO;
import es.redmic.models.es.common.query.dto.MgetDTO;
import es.redmic.models.es.common.utils.HierarchicalUtils;

@RestController
@RequestMapping(value = "${controller.mapping.TAXONS}")
public class TaxonController
		extends RWController<Taxon, es.redmic.models.es.administrative.taxonomy.model.Taxon, TaxonDTO, MetadataQueryDTO> {

	@Autowired
	public TaxonController(IServiceRW<Taxon, TaxonDTO> service,
			@Qualifier("TaxonServiceES") TaxonESService serviceES) {
		super(service, serviceES);
	}
	
	@RequestMapping(value = "${contoller.mapping.ANCESTORS}/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO _getAncestors(@PathVariable("path") String path, HttpServletResponse response, @Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult) {

		processQuery(queryDTO, bindingResult);

		String[] ancestorIds = HierarchicalUtils.getAncestorsIds(path);
		MgetDTO mgetDto = new MgetDTO(Arrays.asList(ancestorIds));
		
		if (queryDTO.getReturnFields() != null && queryDTO.getReturnFields().size() > 0) {
			mgetDto.setFields(queryDTO.getReturnFields());
			if (!mgetDto.getFields().contains("path")) {
				mgetDto.getFields().add("path");
			}
		}
		
		JSONCollectionDTO result = ESService.mget(mgetDto);
		return new ElasticSearchDTO(result, result.getTotal());
	}
}