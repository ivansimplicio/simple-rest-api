package com.dev.service.exception;

public class ObjectNotFoundException extends RuntimeException{
	
	public static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException(String message) {
		super(message);
	}

}