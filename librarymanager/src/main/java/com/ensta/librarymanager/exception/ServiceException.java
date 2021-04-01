package com.ensta.librarymanager.exception;

public class ServiceException extends Exception {

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
        System.out.println("\n");
		cause.printStackTrace();
	}

	public ServiceException(String message) {
		super(message);
	}

}
