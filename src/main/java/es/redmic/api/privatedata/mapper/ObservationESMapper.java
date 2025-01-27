package es.redmic.api.privatedata.mapper;

/*-
 * #%L
 * API
 * %%
 * Copyright (C) 2024 REDMIC Project / Server
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

import org.springframework.stereotype.Component;

import es.redmic.api.privatedata.dto.ObservationDTO;
import es.redmic.api.privatedata.model.Observation;
import es.redmic.models.es.administrative.dto.OrganisationCompactDTO;
import es.redmic.models.es.administrative.taxonomy.dto.AnimalTaxonomyCompactDTO;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonomyCompactDTO;
import es.redmic.models.es.common.dto.DomainDTO;
import es.redmic.models.es.maintenance.device.dto.DeviceCompactDTO;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

@Component
public class ObservationESMapper extends CustomMapper<Observation, ObservationDTO> {

	@Override
	public void mapAtoB(Observation a, ObservationDTO b, MappingContext context) {

		b.setTaxonomy(mapperFacade.map(a.getTaxonomy(), TaxonomyCompactDTO.class));
		b.setAnimal(mapperFacade.map(a.getAnimal(), AnimalTaxonomyCompactDTO.class));
		b.setDevice(mapperFacade.map(a.getDevice(), DeviceCompactDTO.class));
		b.setOrganisation(mapperFacade.map(a.getOrganisation(), OrganisationCompactDTO.class));
		b.setObservationType(mapperFacade.map(a.getObservationType(), DomainDTO.class));
	}
}
