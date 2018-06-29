package es.redmic.api.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.exception.common.BadRequestException;
import es.redmic.exception.dto.ErrorDTO;
import es.redmic.exception.handler.BaseExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class BadRequestExceptionHandler extends BaseExceptionHandler {

	@ExceptionHandler(value = BadRequestException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)  // 400
	@ResponseBody
	public ErrorDTO handleBadRequestException(HttpServletRequest req, HttpServletResponse resp, BadRequestException e) {
		return getError(e);
	}
}
