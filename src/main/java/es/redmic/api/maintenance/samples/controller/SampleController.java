package es.redmic.api.maintenance.samples.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${controller.mapping.SAMPLE}")
public class SampleController /*extends RWController<Sample, SampleDTO>*/ {

	//@Autowired
	public SampleController(/*SampleService service, SampleTypeESService serviceES*/) {
		//super(service, serviceES);
	}
}