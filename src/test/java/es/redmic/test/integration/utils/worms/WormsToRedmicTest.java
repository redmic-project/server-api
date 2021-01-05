package es.redmic.test.integration.utils.worms;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.redmic.ApiApplication;
import es.redmic.api.administrative.taxonomy.controller.WormsController;
import es.redmic.db.administrative.taxonomy.service.SpeciesService;
import es.redmic.db.administrative.taxonomy.service.TaxonService;
import es.redmic.es.administrative.taxonomy.service.TaxonESService;
import es.redmic.es.administrative.taxonomy.service.WormsToRedmicService;
import es.redmic.exception.elasticsearch.ESInsertException;
import es.redmic.models.es.administrative.taxonomy.dto.SpeciesDTO;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonDTO;
import es.redmic.models.es.common.dto.SuperDTO;
import es.redmic.test.integration.ApiApplicationTest;
import es.redmic.test.integration.common.IntegrationTestBase;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ApiApplication.class,
		ApiApplicationTest.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WormsToRedmicTest extends IntegrationTestBase {

	@MockBean
	WormsController controller;

	@MockBean
	TaxonService taxonService;

	@MockBean
	SpeciesService speciesService;

	@MockBean
	TaxonESService taxonESService;

	@Autowired
	WormsToRedmicService service;

	// @formatter:off

	private final String WORMS_URL = "/taxons/worms";

	// @formatter:on

	@Test
	public void convert2redmicAsAdministrator_ReturnSuccess_IfIsAdministrator() throws Exception {

		when(controller.convert2redmic(anyInt())).thenReturn(new SuperDTO(true));

		mockMvc.perform(
				post(WORMS_URL + "/convert2redmic/136470").header("Authorization", "Bearer " + getTokenOAGUser()))
				.andExpect(status().isOk());
	}

	@Test
	public void convert2redmicAsAdministrator_ReturnUnauthorized_IfIsAnonimous() throws Exception {

		mockMvc.perform(post(WORMS_URL + "/convert2redmic/136470")).andExpect(status().isUnauthorized());
	}

	@Test(expected = ESInsertException.class)
	public void convert2redmic_ThrowException_WhenAphiaExistsInRedmic() throws Exception {

		when(taxonESService.findByAphia(anyInt())).thenReturn(new TaxonDTO());

		service.wormsToRedmic(816266);
	}

	/*
	 * Test con un caso real de una specie que no tiene ninguno de sus ancestors en
	 * redmic
	 */
	//@Test
	public void convert2redmic_SaveAllAncestors_WhenNoExistsInRedmic() throws Exception {

		int aphia = 394531;

		when(taxonService.save(any())).thenReturn(new TaxonDTO());
		when(speciesService.save(any())).thenReturn(new SpeciesDTO());

		TaxonDTO taxon = service.wormsToRedmic(aphia);

		// El taxon devuelto debe tener los datos obtenidos de worms
		assertEquals(taxon.getAphia().intValue(), aphia);
		assertEquals(taxon.getScientificName(), "Acanthocorbis brevipoda");

		// Se debe guardar los 7 ancestors + validAs y sus 7 ancestors (6 de los
		// ancestors se repiten al no guardarse, ya que se vuelven a procesar)
		verify(taxonService, times(14)).save(any());
		verify(speciesService, times(1)).save(any());

	}

	@Test
	public void convert2redmicUpdateAsAdministrator_ReturnSuccess_IfIsAdministrator() throws Exception {

		when(controller.convert2redmic(anyInt())).thenReturn(new SuperDTO(true));

		mockMvc.perform(
				put(WORMS_URL + "/convert2redmic/136470").header("Authorization", "Bearer " + getTokenOAGUser()))
				.andExpect(status().isOk());
	}

	@Test
	public void convert2redmicUpdateAsAdministrator_ReturnUnauthorized_IfIsAnonimous() throws Exception {

		mockMvc.perform(put(WORMS_URL + "/convert2redmic/136470")).andExpect(status().isUnauthorized());
	}
}
