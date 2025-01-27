package es.redmic.api.privatedata.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.redmic.models.es.administrative.dto.OrganisationCompactDTO;

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


import es.redmic.models.es.administrative.taxonomy.dto.AnimalTaxonomyCompactDTO;
import es.redmic.models.es.administrative.taxonomy.dto.TaxonomyCompactDTO;
import es.redmic.models.es.common.dto.DomainDTO;
import es.redmic.models.es.maintenance.device.dto.DeviceCompactDTO;

public class ObservationDTO {

	private String note;

	@JsonIgnoreProperties(value = {"_meta"})
	@NotNull
	private TaxonomyCompactDTO taxonomy;

	@JsonIgnoreProperties(value = {"_meta"})
	@NotNull
	private DeviceCompactDTO device;

	@JsonIgnoreProperties(value = {"_meta"})
	@NotNull
	private AnimalTaxonomyCompactDTO animal;

	@JsonIgnoreProperties(value = {"_meta"})
	private OrganisationCompactDTO organisation;

	@JsonIgnoreProperties(value = {"_meta"})
	@NotNull
	private DomainDTO observationType;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public TaxonomyCompactDTO getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(TaxonomyCompactDTO taxonomy) {
		this.taxonomy = taxonomy;
	}

	public DeviceCompactDTO getDevice() {
		return device;
	}

	public void setDevice(DeviceCompactDTO device) {
		this.device = device;
	}

	public AnimalTaxonomyCompactDTO getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalTaxonomyCompactDTO animal) {
		this.animal = animal;
	}

	public OrganisationCompactDTO getOrganisation() {
		return organisation;
	}

	public void setOrganisation(OrganisationCompactDTO organisation) {
		this.organisation = organisation;
	}

	public DomainDTO getObservationType() {
		return observationType;
	}

	public void setObservationType(DomainDTO observationType) {
		this.observationType = observationType;
	}
}
