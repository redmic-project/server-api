package es.redmic.api.common.service;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import es.redmic.es.common.service.UserUtilsServiceItfc;
import es.redmic.exception.common.ExceptionType;
import es.redmic.exception.common.InternalException;
import es.redmic.utils.httpclient.HttpClient;

@SessionScope
@Service
public class UserUtilsService implements UserUtilsServiceItfc {

	protected static Logger logger = LogManager.getLogger();

	HttpClient client = new HttpClient();

	@Value("${oauth.userid.endpoint}")
	String GET_USERID_URL;

	String userId;

	@Override
	public String getUserId() {

		if (userId == null) {
			OAuth2Authentication oauth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
			OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oauth.getDetails();
			String token = details.getTokenValue();

			if (token == null) {
				logger.error("Imposible recuperar el token en UserBaseService. Usuario no registrado correctamente.");
				throw new InternalException(ExceptionType.INTERNAL_EXCEPTION);
			}

			return (String) client.get(GET_USERID_URL + "?access_token=" + token, String.class);
		}
		return userId;
	}

	@Override
	public List<String> getUserRole() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		if (authorities == null || authorities.size() == 0)
			return null;

		List<String> roles = new ArrayList<String>();

		for (GrantedAuthority authority : authorities) {
			roles.add(authority.getAuthority());
		}
		return roles;
	}

	@Override
	public List<Long> getAccessibilityControl() {

		List<Long> accessibilities = new ArrayList<Long>();

		if (getUserRole().contains("ROLE_ANONYMOUS")) {
			accessibilities.add(2L);
		}
		return accessibilities;
	}
}
