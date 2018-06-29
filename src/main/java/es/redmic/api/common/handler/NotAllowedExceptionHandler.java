package es.redmic.api.common.handler;

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
