package es.redmic.api.privatedata.dto;

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

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaIgnore;

import es.redmic.models.es.common.deserializer.CustomDateTimeDeserializer;
import es.redmic.models.es.common.dto.DTOImplementWithMeta;
import es.redmic.models.es.common.serializer.CustomDateTimeSerializer;

@JsonIgnoreProperties(value = {"_meta"}, ignoreUnknown = true)
public class ObservationSeriesDTO extends DTOImplementWithMeta {

	@NotNull
	private Long activityId;

	@NotNull
	private Long featureId;

	@NotNull
	private Double value;

	@NotNull
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private DateTime date;

	@NotNull
	private Long dataDefinition;

	private Character qFlag = '0';

	private Character vFlag = 'U';

	@JsonSchemaIgnore
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private DateTime inserted;

	@JsonSchemaIgnore
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	private DateTime updated;

	@JsonIgnoreProperties(value = {"_meta"})
	@NotNull
	private ObservationDTO observation;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public Long getDataDefinition() {
		return dataDefinition;
	}

	public void setDataDefinition(Long dataDefinition) {
		this.dataDefinition = dataDefinition;
	}


	@JsonProperty(value = "qFlag")
	public Character getQFlag() {
		return qFlag;
	}

	@JsonProperty(value = "qFlag")
	public void setQFlag(Character qFlag) {

		if (qFlag != null)
			this.qFlag = qFlag;
	}

	@JsonIgnore
	public void setqFlag(Character qFlag) {
		this.qFlag = qFlag;
	}

	@JsonProperty(value = "vFlag")
	public Character getVFlag() {
		return vFlag;
	}

	@JsonProperty(value = "vFlag")
	public void setVFlag(Character vFlag) {

		if (vFlag != null)
			this.vFlag = vFlag;
	}

	@JsonIgnore
	public void setvFlag(Character vFlag) {
		this.vFlag = vFlag;
	}

	public DateTime getInserted() {
		return inserted;
	}

	public void setInserted(DateTime inserted) {
		this.inserted = inserted;
	}

	public DateTime getUpdated() {
		return updated;
	}

	public void setUpdated(DateTime updated) {
		this.updated = updated;
	}

	public ObservationDTO getObservation() {
		return observation;
	}

	public void setObservation(ObservationDTO observation) {
		this.observation = observation;
	}
}
