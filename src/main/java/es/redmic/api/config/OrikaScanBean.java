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

import java.util.Map;

import org.joda.time.DateTime;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import es.redmic.db.config.OrikaScanBeanDBItfc;
import es.redmic.es.common.objectFactory.ModelESFactory;
import es.redmic.es.config.OrikaScanBeanESItfc;
import es.redmic.es.geodata.common.objectfactory.GeometryESFactory;
import es.redmic.models.es.common.model.BaseES;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.Filter;
import ma.glasnost.orika.Mapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.TypeFactory;

/**
 * A bean mapper designed for Spring suitable for dependency injection.
 *
 * Provides an implementation of {@link MapperFacade} which can be injected. In
 * addition it is "Spring aware" in that it can autodiscover any implementations
 * of {@link Mapper} or {@link Converter} that are managed beans within it's
 * parent {@link ApplicationContext}.
 *
 * @author Ken Blair
 */
@Component
public class OrikaScanBean extends ConfigurableMapper
		implements ApplicationContextAware, OrikaScanBeanDBItfc, OrikaScanBeanESItfc {

	public OrikaScanBean() {
		super(false);
	}

	private MapperFactory factory;

	private ApplicationContext applicationContext;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configureFactoryBuilder(final DefaultMapperFactory.Builder factoryBuilder) {
		// customize the factoryBuilder as needed
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void configure(final MapperFactory factory) {

		this.factory = factory;
		addAllSpringBeans();
		addConverter(new PassThroughConverter(org.joda.time.DateTime.class));

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		this.init();
	}

	/**
	 * Adds all managed beans of type {@link Mapper} or {@link Converter} to the
	 * parent {@link MapperFactory}.
	 *
	 * @param applicationContext
	 *            The application context to look for managed beans in.
	 */
	@Override
	public void addAllSpringBeans() {
		@SuppressWarnings("rawtypes")
		final Map<String, Converter> converters = applicationContext.getBeansOfType(Converter.class);
		for (@SuppressWarnings("rawtypes")
		final Converter converter : converters.values()) {
			addConverter(converter);
		}

		@SuppressWarnings("rawtypes")
		final Map<String, CustomMapper> mappers = applicationContext.getBeansOfType(CustomMapper.class);
		for (@SuppressWarnings("rawtypes")
		final CustomMapper mapper : mappers.values()) {
			addMapper(mapper);
		}

		/*
		 * @SuppressWarnings("rawtypes") final Map<String, Filter> filters =
		 * applicationContext.getBeansOfType(Filter.class); for
		 * (@SuppressWarnings("rawtypes") final Filter filter : filters.values()) {
		 * addFilter(filter); }
		 */

		addObjectFactory();
		addDefaultActions();
	}

	/**
	 * Add a {@link Converter}.
	 *
	 * @param converter
	 *            The converter.
	 */
	@Override
	public void addConverter(final Converter<?, ?> converter) {
		factory.getConverterFactory().registerConverter(converter);
	}

	/**
	 * Add a {@link Mapper}.
	 *
	 * @param mapper
	 *            The mapper.
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addMapper(final CustomMapper<?, ?> mapper) {
		factory.classMap(mapper.getAType(), mapper.getBType()).byDefault().customize((CustomMapper) mapper).register();
	}

	public void addFilter(final Filter<?, ?> filter) {
		factory.registerFilter(filter);
	}

	public void addObjectFactory() {
		factory.registerObjectFactory(new ModelESFactory(), TypeFactory.<BaseES<?>>valueOf(BaseES.class));
	}

	private void addDefaultActions() {

		factory.classMap(Point.class, Point.class).customize(new CustomMapper<Point, Point>() {
		}).register();

		factory.registerObjectFactory(new GeometryESFactory<Point>(), TypeFactory.<Point>valueOf(Point.class));

		factory.classMap(LineString.class, LineString.class).customize(new CustomMapper<LineString, LineString>() {
		}).register();

		factory.registerObjectFactory(new GeometryESFactory<LineString>(),
				TypeFactory.<LineString>valueOf(LineString.class));

		factory.classMap(Polygon.class, Polygon.class).customize(new CustomMapper<Polygon, Polygon>() {
		}).register();

		factory.registerObjectFactory(new GeometryESFactory<Polygon>(), TypeFactory.<Polygon>valueOf(Polygon.class));

		factory.classMap(MultiPolygon.class, MultiPolygon.class)
				.customize(new CustomMapper<MultiPolygon, MultiPolygon>() {
				}).register();

		factory.registerObjectFactory(new GeometryESFactory<MultiPolygon>(),
				TypeFactory.<MultiPolygon>valueOf(MultiPolygon.class));

		factory.classMap(MultiLineString.class, MultiLineString.class)
				.customize(new CustomMapper<MultiLineString, MultiLineString>() {
				}).register();

		factory.registerObjectFactory(new GeometryESFactory<MultiLineString>(),
				TypeFactory.<MultiLineString>valueOf(MultiLineString.class));


		factory.classMap(Geometry.class, Geometry.class)
				.customize(new CustomMapper<Geometry, Geometry>() {
				}).register();

		factory.registerObjectFactory(new GeometryESFactory<Geometry>(),
				TypeFactory.<Geometry>valueOf(Geometry.class));

		factory.getConverterFactory().registerConverter(new PassThroughConverter(DateTime.class));
		factory.getConverterFactory().registerConverter(new PassThroughConverter(Point.class));
		factory.getConverterFactory().registerConverter(new PassThroughConverter(LineString.class));
		factory.getConverterFactory().registerConverter(new PassThroughConverter(Polygon.class));
		factory.getConverterFactory().registerConverter(new PassThroughConverter(MultiPolygon.class));
		factory.getConverterFactory().registerConverter(new PassThroughConverter(MultiLineString.class));
		factory.getConverterFactory().registerConverter(new PassThroughConverter(Geometry.class));
	}

	@Override
	public MapperFacade getMapperFacade() {
		return factory.getMapperFacade();

	}
}
