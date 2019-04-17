package es.redmic;

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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import es.redmic.api.common.converter.QueryDTOMessageConverter;
import es.redmic.api.config.GenerateJsonSchemaScanBean;
import es.redmic.api.config.OrikaScanBean;
import es.redmic.api.config.ResourceBundleMessageSource;
import es.redmic.databaselib.common.repository.BaseRepositoryImpl;
import es.redmic.db.config.EntityManagerWrapper;
import es.redmic.es.common.service.UserUtilsServiceItfc;
import es.redmic.models.es.common.view.QueryDTODeserializerModifier;
import io.micrometer.core.instrument.MeterRegistry;

@SpringBootApplication(exclude = { MongoAutoConfiguration.class, ElasticsearchAutoConfiguration.class })
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan({ "es.redmic.api", "es.redmic.es", "es.redmic.databaselib", "es.redmic.db", "es.redmic.mediastorage",
		"es.redmic.utils" })
@EnableJpaRepositories(basePackages = { "es.redmic.db",
		"es.redmic.databaselib" }, repositoryBaseClass = BaseRepositoryImpl.class)
public class ApiApplication {

	@Value("${info.microservice.name}")
	String microserviceName;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	UserUtilsServiceItfc userService;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public EntityManagerWrapper entityManagerWrapper() {
		return new EntityManagerWrapper();
	}

	@PostConstruct
	@Bean
	public OrikaScanBean orikaScanBean() {
		return new OrikaScanBean();
	}

	@PostConstruct
	@Bean
	public GenerateJsonSchemaScanBean generateSchemaScanBean() {
		return new GenerateJsonSchemaScanBean();
	}

	@Bean
	public MessageSource messageSource() {

		return new ResourceBundleMessageSource();
	}

	@Bean
	public Module jtsModule() {
		return new JtsModule();
	}

	@Bean
	public SimpleModule queryDTOJsonViewModule() {

		return new SimpleModule().setDeserializerModifier(new QueryDTODeserializerModifier());
	}

	@Bean
	public QueryDTOMessageConverter queryDTOMessageConverter() {

		FilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false).addFilter("DataQueryDTO",
				SimpleBeanPropertyFilter.serializeAll());
		objectMapper.setFilterProvider(filters);

		return new QueryDTOMessageConverter(objectMapper, userService);
	}

	@Bean
	MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
		return registry -> registry.config().commonTags("application", microserviceName);
	}
}
