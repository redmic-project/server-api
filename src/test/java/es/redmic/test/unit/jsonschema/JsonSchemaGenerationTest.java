package es.redmic.test.unit.jsonschema;

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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.core.env.Environment;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import es.redmic.api.config.GenerateJsonSchemaScanBean;
import es.redmic.models.es.administrative.dto.ActivityDTO;
import es.redmic.models.es.geojson.citation.dto.CitationDTO;

@RunWith(MockitoJUnitRunner.class)
public class JsonSchemaGenerationTest {

	@Spy
	ObjectMapper objectMapper;

	@Mock
	private Environment env;

	@InjectMocks
	GenerateJsonSchemaScanBean jsonSchemaUtil;

	private String jsonschemaPath = "/data/jsonschema/activityJsonschema.json",
			geoJsonschemaPath = "/data/jsonschema/geoJsonschema.json";

	@Before
	public void init() throws Exception {

		HashMap<String, Object> properties = new HashMap<>();
		properties.put("controller.mapping.PROJECT", "/operator/projects/");
		properties.put("controller.mapping.ACTIVITY", "/operator/activities/");
		properties.put("controller.mapping.ACCESSIBILITY", "/operator/accessibilities/");
		properties.put("controller.mapping.SCOPES", "/operator/scopes/");
		properties.put("controller.mapping.ACTIVITY_TYPE", "/operator/activitytypes");
		properties.put("controller.mapping.DOCUMENT", "/operator/documents/");
		properties.put("controller.mapping.CONTACT", "/operator/contacts/");
		properties.put("controller.mapping.ORGANISATION", "/operator/organisations/");
		properties.put("controller.mapping.PLATFORM", "/operator/platforms/");
		properties.put("controller.mapping.CONTACT_ROLE", "/operator/contactroles/");
		properties.put("controller.mapping.ORGANISATION_ROLES", "/operator/organisationroles/");
		properties.put("controller.mapping.CONFIDENCE", "/operator/confidences");
		properties.put("controller.mapping.SPECIES", "/operator/taxons/species");
		properties.put("controller.mapping.MISIDENTIFICATION", "/operator/taxons/misidentification");
		properties.put("controller.mapping.THEME_INSPIRE", "/operator/themeinspire");

		Whitebox.setInternalState(jsonSchemaUtil, HashMap.class, properties);
		Whitebox.invokeMethod(jsonSchemaUtil, "jsonSchemaGeneratorInit");
		objectMapper.registerModule(new JodaModule());
		objectMapper.registerModule(new JtsModule());

		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Test
	public void simpleJsonschemaGenerate() throws JSONException, IOException {

		String result = jsonSchemaUtil.generateJsonSchema(ActivityDTO.class),
				expected = IOUtils.toString(getClass().getResource(jsonschemaPath).openStream(),
					Charset.forName(StandardCharsets.UTF_8.name()));
		JSONAssert.assertEquals(result, expected, true);
	}

	@Test
	public void geoJsonschemaGenerate() throws JSONException, IOException {

		String result = jsonSchemaUtil.generateJsonSchema(CitationDTO.class),
				expected = IOUtils.toString(getClass().getResource(geoJsonschemaPath).openStream(),
					Charset.forName(StandardCharsets.UTF_8.name()));
		JSONAssert.assertEquals(result, expected, true);
	}
}
