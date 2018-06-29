package es.redmic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
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

@SpringBootApplication(exclude = { MongoAutoConfiguration.class, ElasticsearchAutoConfiguration.class })
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan({ "es.redmic.api", "es.redmic.es", "es.redmic.databaselib", "es.redmic.db", "es.redmic.mediastorage",
		"es.redmic.utils" })
@EnableJpaRepositories(basePackages = { "es.redmic.db",
		"es.redmic.databaselib" }, repositoryBaseClass = BaseRepositoryImpl.class)
public class ApiApplication {

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
}