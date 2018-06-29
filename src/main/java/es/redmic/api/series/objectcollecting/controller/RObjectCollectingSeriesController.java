package es.redmic.api.series.objectcollecting.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.api.series.common.controller.RSeriesController;
import es.redmic.es.series.objectcollecting.service.ObjectCollectingSeriesESService;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.models.es.common.query.dto.AggsPropertiesDTO;
import es.redmic.models.es.common.query.dto.DataQueryDTO;
import es.redmic.models.es.series.objectcollecting.dto.ObjectCollectingSeriesDTO;
import es.redmic.models.es.series.objectcollecting.model.ObjectCollectingSeries;

@RestController
@RequestMapping(value = "${controller.mapping.OBJECTCOLLECTING}")
public class RObjectCollectingSeriesController extends RSeriesController<ObjectCollectingSeries, ObjectCollectingSeriesDTO, DataQueryDTO> {

	private ObjectCollectingSeriesESService service;

	@Autowired
	public RObjectCollectingSeriesController(ObjectCollectingSeriesESService service) {
		super(service);
		this.service = service;
	}
	
	@RequestMapping(value = "${controller.mapping.OBJECT_CLASSIFICATION_LIST}/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO findClassificationList(@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult) {

		AggsPropertiesDTO agg = new AggsPropertiesDTO();
		agg.setField("classificationList");
		queryDTO.addAgg(agg);
		queryDTO.setSize(0);
		
		processQuery(queryDTO, bindingResult);
		
		return service.findClassificationList(queryDTO);
	}
	
	@RequestMapping(value = "${controller.mapping.OBJECT_CLASSIFICATION}/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO findClassification(@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult) {

		AggsPropertiesDTO agg = new AggsPropertiesDTO();
		agg.setField("classification");
		queryDTO.addAgg(agg);
		queryDTO.setSize(0);
		
		processQuery(queryDTO, bindingResult);
		
		return service.findClassificationStatistics(queryDTO);
	}
	
	@RequestMapping(value = "${controller.mapping.SERIES_TEMPORALDATA}/_search", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO findTemporalData(@Valid @RequestBody DataQueryDTO queryDTO, BindingResult bindingResult) {
		
		AggsPropertiesDTO agg = new AggsPropertiesDTO();
		agg.setField("temporaldata");
		queryDTO.addAgg(agg);
		
		queryDTO.setSize(0);
		
		processQuery(queryDTO, bindingResult);
		
		return service.findTemporalDataStatistics(queryDTO);
	}
}