package com.dev.dto.usuario;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioOutputDTO extends RepresentationModel<UsuarioOutputDTO> implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String cpf;
	private String email;

}