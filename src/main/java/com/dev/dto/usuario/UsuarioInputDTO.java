package com.dev.dto.usuario;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInputDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 8, max = 30, message = "O campo deve conter entre 8 e 30 caracteres.")
	private String nome;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 14, max = 14, message = "O campo deve conter 14 caracteres.")
	private String CPF;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 8, max = 30, message = "O campo deve conter entre 8 e 30 caracteres.")
	@Email(message = "Email inválido.")
	private String email;

}