package com.cashregister.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Component
@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// @ExceptionHandler(Exception.class)
	// public final ResponseEntity<Advice> handleUserNotFoundException(Exception ex)
	// {
	//
	// Advice apiException = new Advice(HttpStatus.INTERNAL_SERVER_ERROR,
	// HttpStatus.INTERNAL_SERVER_ERROR.value(),
	// ex.getClass().getSimpleName(), ex);
	// Advice.SubAdvice subException = apiException.new SubAdvice(ex.getMessage(),
	// ex.getMessage());
	// apiException.setSubErrors(subException);
	//
	// return new ResponseEntity<Advice>(apiException,
	// HttpStatus.INTERNAL_SERVER_ERROR);
	// }

}
