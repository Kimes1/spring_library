package com.niedzielski.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.niedzielski.exception.BookRentedByUserException;
import com.niedzielski.exception.CopyUnavailableException;
import com.niedzielski.exception.UserExistException;
import com.niedzielski.exception.UserNotExistException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CopyUnavailableException.class)
	public ResponseEntity<Object> badRequestCopyUnavailable(Exception ex) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", ex.getMessage());
		return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserExistException.class)
	public ResponseEntity<Object> badRequestUserExist(Exception ex) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", ex.getMessage());
		return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BookRentedByUserException.class)
	public ResponseEntity<Object> badRequestAlreadyLentByUser(Exception ex) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", ex.getMessage());
		return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserNotExistException.class)
	public ResponseEntity<Object> badRequestUserDoesNotExist(Exception ex) {
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", ex.getMessage());
		return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
	}
}