package com.niedzielski.exception;

public class BookRentedByUserException extends Exception {

	private static final long serialVersionUID = 1L;

	public BookRentedByUserException(String msg) {
		super(msg);
	}

}
