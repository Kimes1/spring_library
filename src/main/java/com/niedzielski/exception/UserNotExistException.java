package com.niedzielski.exception;

public class UserNotExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotExistException(String msg) {
		super(msg);
	}
}
