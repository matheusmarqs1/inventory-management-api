package com.matheusmarqs1.inventory_management_api.exceptions;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request){
		String error = "Resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<StandardError> handleBusinessException(BusinessException e, HttpServletRequest request){
		String error = "Business rule violation";
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<StandardError>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		List<StandardError> errors = e.getBindingResult().
				getFieldErrors()
				.stream()
				.map(fieldError -> new StandardError(
						Instant.now(), 
						status.value(), 
						fieldError.getField() + ": " + fieldError.getDefaultMessage(), 
						"Validation failed", 
						request.getRequestURI())).toList();
		return ResponseEntity.status(status).body(errors);
		
	}
}
