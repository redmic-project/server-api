package es.redmic.api.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.exception.common.ConflictException;
import es.redmic.exception.dto.ErrorDTO;
import es.redmic.exception.handler.BaseExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class ConflictExceptionHandler extends BaseExceptionHandler {

	@ExceptionHandler(value = ConflictException.class)
	@ResponseStatus(value=HttpStatus.CONFLICT)  // 409
	@ResponseBody
	public ErrorDTO handleConflictException(HttpServletRequest req, HttpServletResponse resp, ConflictException e) {
		return getError(e);
	}
}
