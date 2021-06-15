package com.dev.service.exception;

public class DataIntegrityException extends RuntimeException{
	
	public static final long serialVersionUID = 1L;
	
	public DataIntegrityException(String message) {
		super(message);
	}
}