package es.redmic.api.config;

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

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import com.kjetland.jackson.jsonSchema.JsonSchemaResources;

import es.redmic.api.common.controller.RWController;
import es.redmic.api.geodata.common.controller.RWGeoDataController;
import es.redmic.api.series.common.controller.RWSeriesController;
import es.redmic.api.series.common.controller.RWSeriesWithOutDataDefinitionController;
import es.redmic.mediastorage.service.MediaStorageService;
import es.redmic.models.es.administrative.dto.ActivityPlatformRoleDTO;
import es.redmic.models.es.administrative.dto.ContactOrganisationRoleDTO;
import es.redmic.models.es.administrative.dto.OrganisationRoleDTO;
import es.redmic.models.es.administrative.taxonomy.dto.RecoveryDTO;
import es.redmic.models.es.administrative.taxonomy.dto.SpecimenTagDTO;
import es.redmic.models.es.atlas.dto.LayerCompactDTO;
import es.redmic.models.es.geojson.area.dto.AreaPropertiesDTO;
import es.redmic.models.es.maintenance.areas.dto.AreaClassificationDTO;
import es.redmic.models.es.maintenance.device.dto.CalibrationDTO;
import es.redmic.models.es.maintenance.survey.dto.FixedSurveyDTO;
import es.redmic.models.es.series.common.dto.MeasurementDTO;

public class GenerateJsonSchemaScanBean implements ApplicationContextAware {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	MediaStorageService mediaStorageService;

	HashMap<String, Object> properties = new HashMap<>();

	@Autowired
	private Environment env;

	JsonSchemaGenerator jsonSchemaGenerator;

	private ApplicationContext applicationContext;

	@Value("${property.path.media_storage.JSONSCHEMA}")
	private String jsonschemaPath;

	private List<Class<?>> specialBeansToGenerate = new ArrayList<Class<?>>(Arrays.asList(RecoveryDTO.class,
			SpecimenTagDTO.class, ContactOrganisationRoleDTO.class, OrganisationRoleDTO.class,
			ActivityPlatformRoleDTO.class, FixedSurveyDTO.class, MeasurementDTO.class, LayerCompactDTO.class,
			CalibrationDTO.class, AreaClassificationDTO.class, AreaPropertiesDTO.class));

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		jsonSchemaGeneratorInit();
		addAllControllerBeans();
	}

	/**
	 * Recorre todos los beans de tipo Lectura-Escritura para generar los
	 * esquemas
	 */
	public void addAllControllerBeans() {
		/* Genera los esquemas de json de datos simples */
		@SuppressWarnings("rawtypes")
		final Map<String, RWController> controllers = applicationContext.getBeansOfType(RWController.class);
		for (@SuppressWarnings("rawtypes")
		final RWController controller : controllers.values()) {

			try {
				Class<?> typeOfTDTO = (Class<?>) ((ParameterizedType) controller.getClass().getGenericSuperclass())
						.getActualTypeArguments()[2];

				generateAndSaveJsonSchema(typeOfTDTO);

			} catch (Exception e) {
				System.err.println("Error al generar el jsonSchema " + controller.getClass());
				e.printStackTrace();
			}
		}

		/* Genera los esquemas de Json de geodata */
		@SuppressWarnings("rawtypes")
		final Map<String, RWGeoDataController> featureControllers = applicationContext
				.getBeansOfType(RWGeoDataController.class);
		for (@SuppressWarnings("rawtypes")
		final RWGeoDataController controller : featureControllers.values()) {
			try {
				Class<?> typeOfTDTO = (Class<?>) ((ParameterizedType) controller.getClass().getGenericSuperclass())
						.getActualTypeArguments()[2];
				generateAndSaveJsonSchema(typeOfTDTO);
			} catch (Exception e) {
				System.err.println("Error al generar el jsonSchema " + controller.getClass());
				e.printStackTrace();
			}
		}

		@SuppressWarnings("rawtypes")
		final Map<String, RWSeriesWithOutDataDefinitionController> seriesNewControllers = applicationContext
				.getBeansOfType(RWSeriesWithOutDataDefinitionController.class);
		for (@SuppressWarnings("rawtypes")
		final RWSeriesWithOutDataDefinitionController controller : seriesNewControllers.values()) {

			try {
				Class<?> typeOfTDTO = (Class<?>) ((ParameterizedType) controller.getClass().getGenericSuperclass())
						.getActualTypeArguments()[2];
				generateAndSaveJsonSchema(typeOfTDTO);

			} catch (Exception e) {
				System.err.println("Error al generar el jsonSchema " + controller.getClass());
				e.printStackTrace();
			}
		}

		// TODO: eliminar cuando las series se pasen a lo nuevo (filtrado
		// siempre por actividad y geodata
		/* Genera los esquemas de Json de series */
		@SuppressWarnings("rawtypes")
		final Map<String, RWSeriesController> seriesControllers = applicationContext
				.getBeansOfType(RWSeriesController.class);
		for (@SuppressWarnings("rawtypes")
		final RWSeriesController controller : seriesControllers.values()) {

			try {
				Class<?> typeOfTDTO = (Class<?>) ((ParameterizedType) controller.getClass().getGenericSuperclass())
						.getActualTypeArguments()[1];
				generateAndSaveJsonSchema(typeOfTDTO);

			} catch (Exception e) {
				System.err.println("Error al generar el jsonSchema " + controller.getClass());
				e.printStackTrace();
			}
		}

		for (int i = 0; i < specialBeansToGenerate.size(); i++) {

			try {
				generateAndSaveJsonSchema(specialBeansToGenerate.get(i));
			} catch (Exception e) {
				System.err.println("Error al generar el jsonSchema " + specialBeansToGenerate.get(i).getClass());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Genera y guarda el json esquema para el dto del controlador pasado
	 * 
	 * @param typeOfTDTO
	 *            Clase del dto del cual se generará el esquema
	 * @throws JsonProcessingException
	 */
	private void generateAndSaveJsonSchema(Class<?> typeOfTDTO) throws JsonProcessingException {

		String result = generateJsonSchema(typeOfTDTO);
		if (result != null)
			saveJsonSchema(result, typeOfTDTO.getName());
	}

	public String generateJsonSchema(Class<?> typeOfTDTO) throws JsonProcessingException {
		JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(typeOfTDTO);

		return objectMapper.writeValueAsString(jsonSchema);
	}

	private void saveJsonSchema(String jsonSchema, String name) {

		mediaStorageService.saveTempFile(jsonSchema.getBytes(), jsonschemaPath, name + ".json");
	}

	private void jsonSchemaGeneratorInit() {

		jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper, JsonSchemaResources.setResources(getProperties()));
	}

	@SuppressWarnings({ "rawtypes" })
	public Map<String, Object> getProperties() {

		if (properties.isEmpty()) {
			String serverPath = env.getProperty("server.servlet.context-path");
			for (Iterator it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext();) {
				PropertySource propertySource = (PropertySource) it.next();
				if (propertySource instanceof MapPropertySource) {
					Map<String, Object> envProperties = ((MapPropertySource) propertySource).getSource();
					for (Map.Entry<String, Object> entry : envProperties.entrySet()) {
						if (entry.getKey().toString().contains("controller.mapping"))
							properties.put(entry.getKey(),
									serverPath + applicationContext.getEnvironment().getProperty(entry.getKey()));
					}
				}
			}
		}
		return properties;
	}

}
