package com.ensta.librarymanager.exception;

public class DaoException extends Exception {

	public DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
		System.out.println("\n");
		cause.printStackTrace();
	}

	public DaoException(String message) {
		super(message);
	}
	
}
