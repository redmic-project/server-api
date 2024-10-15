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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
public class Oauth2SecurityConfiguration {

	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

		private static final String SPARKLR_RESOURCE_ID = "sparklr";

		@Value("${oauth.check_token.endpoint}")
		String checkTokenEndpoint;
		@Value("${oauth.client.id}")
		String clientId;
		@Value("${oauth.client.secret}")
		String secret;

		@Primary
		@Bean
		public RemoteTokenServices tokenService() {
			RemoteTokenServices tokenService = new RemoteTokenServices();
			tokenService.setCheckTokenEndpointUrl(checkTokenEndpoint);
			tokenService.setClientId(clientId);
			tokenService.setClientSecret(secret);
			return tokenService;
		}

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) {
			resources.tokenServices(tokenService()).resourceId(SPARKLR_RESOURCE_ID);
		}

		@Override
		public void configure(HttpSecurity http) throws Exception {
			// @formatter:off

			http.cors();

			http.authorizeRequests().antMatchers(HttpMethod.POST, "/**/_search").permitAll();

			http.authorizeRequests().antMatchers(HttpMethod.POST, "/**/_categories").permitAll();

			http.authorizeRequests().antMatchers(HttpMethod.POST, "/**/_mget").permitAll();

			http.authorizeRequests().antMatchers(HttpMethod.POST, "/**/_suggest").permitAll();

			http.authorizeRequests().antMatchers("/**/_selection/**").permitAll();

			http.authorizeRequests().antMatchers("/**/_selections/**").access(
				"#oauth2.hasScope('write') and "
				+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR', 'ROLE_USER')");

			http.authorizeRequests().antMatchers(HttpMethod.GET, "/**/_search/_schema").permitAll();

			http.authorizeRequests().antMatchers(HttpMethod.POST, "/**/utils/geo/convert2geojson").permitAll();

			http.authorizeRequests().antMatchers(HttpMethod.GET, "/**/activitycategories").access(
					"#oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR')");

			http.authorizeRequests().antMatchers(HttpMethod.GET, "/**/_schema").access(
					"#oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR')");

			http.authorizeRequests().antMatchers(HttpMethod.POST, "/mediastorage/uploads/users").access(
					"#oauth2.hasScope('read') or #oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR', 'ROLE_USER')");

			http.authorizeRequests().antMatchers(HttpMethod.GET, "/mediastorage/photobank/users/**").access(
					"#oauth2.hasScope('read') or #oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR', 'ROLE_USER')");

			http.authorizeRequests().antMatchers(HttpMethod.GET, "/mediastorage/documents/**").access(
					"#oauth2.hasScope('read') or #oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR', 'ROLE_USER')");

			http.authorizeRequests().antMatchers(HttpMethod.POST, "/mediastorage/**").access(
					"#oauth2.hasScope('read') or #oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR')");

			http.authorizeRequests().antMatchers(HttpMethod.GET, "/mediastorage/**").access(
					"#oauth2.hasScope('read') or #oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR')");

			http.authorizeRequests().antMatchers(HttpMethod.POST, "/**").access(
					"#oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR')");

			http.authorizeRequests().antMatchers(HttpMethod.PUT, "/**").access(
					"#oauth2.hasScope('write') and hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR')");

			http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/**").access(
					"#oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR')");

			http.authorizeRequests().antMatchers("/temp/report/**/").access(
					"#oauth2.hasScope('read') or #oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_MANAGER', 'ROLE_COLLABORATOR', 'ROLE_USER')");

			http.authorizeRequests().antMatchers("/**/convert2redmic/**").access(
					"#oauth2.hasScope('read') or #oauth2.hasScope('write') and "
					+ "hasAnyRole('ROLE_ADMINISTRATOR')");
		}
	}
}
