package es.redmic.api.privatedata.model;

import es.redmic.models.es.administrative.taxonomy.model.AnimalTaxonomyCompact;
import es.redmic.models.es.administrative.taxonomy.model.TaxonomyBase;
import es.redmic.models.es.common.model.DomainES;
import es.redmic.models.es.maintenance.device.model.DeviceCompact;
import es.redmic.models.es.administrative.model.OrganisationCompact;

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

public class Observation {


	private String note;

	private TaxonomyBase taxonomy;

	private DeviceCompact device;

	private AnimalTaxonomyCompact animal;

	private OrganisationCompact organisation;

	private DomainES observationType;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public TaxonomyBase getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(TaxonomyBase taxonomy) {
		this.taxonomy = taxonomy;
	}

	public DeviceCompact getDevice() {
		return device;
	}

	public void setDevice(DeviceCompact device) {
		this.device = device;
	}

	public AnimalTaxonomyCompact getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalTaxonomyCompact animal) {
		this.animal = animal;
	}

	public OrganisationCompact getOrganisation() {
		return this.organisation;
	}

	public void setOrganisation(OrganisationCompact organisation) {
		this.organisation = organisation;
	};

	public DomainES getObservationType() {
		return observationType;
	}

	public void setObservationType(DomainES observationType) {
		this.observationType = observationType;
	}
}
