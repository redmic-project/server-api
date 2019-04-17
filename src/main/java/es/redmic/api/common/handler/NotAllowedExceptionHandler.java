package es.redmic.api.common.handler;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.exception.common.MethodNotAllowedException;
import es.redmic.exception.dto.ErrorDTO;
import es.redmic.exception.handler.BaseExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class NotAllowedExceptionHandler extends BaseExceptionHandler {

	@ExceptionHandler(value = MethodNotAllowedException.class)
	@ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED)  // 405
	@ResponseBody
	public ErrorDTO handleNotFoundException(HttpServletRequest req, HttpServletResponse resp, MethodNotAllowedException e) {
		return getError(e);
	}
}
