package com.dev.resource.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	private String fieldName;
	private String message;

}