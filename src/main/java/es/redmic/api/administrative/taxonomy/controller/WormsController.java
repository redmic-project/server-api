package es.redmic.api.administrative.taxonomy.controller;

/*-
 * #%L
 * API
 * %%
 * Copyright (C) 2019 REDMIC Project / Server
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.es.administrative.taxonomy.service.WormsToRedmicService;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.administrative.taxonomy.dto.WormsDTO;
import es.redmic.models.es.common.dto.BodyItemDTO;
import es.redmic.models.es.common.dto.BodyListDTO;
import es.redmic.models.es.common.dto.SuperDTO;

@RestController
@RequestMapping(value = "${controller.mapping.WORMS}")
public class WormsController {

	@Autowired
	WormsToRedmicService service;

	@RequestMapping(value = "/{aphia}", method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO getWormsRegisterByAphia(@PathVariable("aphia") Integer aphia) {

		return new BodyItemDTO<WormsDTO>(service.getAphiaRecordByAphiaId(aphia));
	}

	@RequestMapping(value = "/", params = { "scientificname" }, method = RequestMethod.GET)
	@ResponseBody
	public SuperDTO getWormsRegistersByScientificName(@RequestParam("scientificname") String scientificname) {

		return new BodyListDTO<WormsDTO>(service.findAphiaRecordsByScientificName(scientificname));
	}

	@RequestMapping(value = "/convert2redmic/{aphia}", method = RequestMethod.POST)
	@ResponseBody
	public SuperDTO convert2redmic(@PathVariable("aphia") Integer aphia) {

		return new BodyItemDTO<TaxonDTO>(service.wormsToRedmic(aphia));
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO update(@PathVariable("id") String id) {

		return new BodyItemDTO<TaxonDTO>(service.update(id));
	}

	@RequestMapping(value = "/convert2redmic/{aphia}", method = RequestMethod.PUT)
	@ResponseBody
	public SuperDTO update2Worms(@PathVariable("aphia") Integer aphia) {

		return new BodyItemDTO<TaxonDTO>(service.wormsToRedmic4Update(aphia));
	}
}
