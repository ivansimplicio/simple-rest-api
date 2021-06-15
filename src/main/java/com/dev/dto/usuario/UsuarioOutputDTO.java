package com.dev.dto.usuario;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioOutputDTO implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String CPF;
	private String email;

}