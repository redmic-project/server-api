package es.redmic.api.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.redmic.exception.common.NotFoundException;
import es.redmic.exception.dto.ErrorDTO;
import es.redmic.exception.handler.BaseExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class NotFoundExceptionHandler extends BaseExceptionHandler {

	@ExceptionHandler(value = NotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)  // 404
	@ResponseBody
	public ErrorDTO handleNotFoundException(HttpServletRequest req, HttpServletResponse resp, NotFoundException e) {
		return getError(e);
	}
}
