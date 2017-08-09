package com.niedzielski.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.niedzielski.exception.CopyUnavailableException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(CopyUnavailableException.class)
	public ResponseEntity<Object> badRequest(Exception ex) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", ex.getMessage());
		return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
	}
}