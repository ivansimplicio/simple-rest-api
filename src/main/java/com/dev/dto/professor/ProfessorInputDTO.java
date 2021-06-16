package com.dev.dto.professor;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.dev.dto.usuario.UsuarioInputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorInputDTO extends UsuarioInputDTO{
	
	public static final long serialVersionUID = 1L;
	
	private Double salario;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 5, max = 30, message = "O campo deve conter entre 5 e 30 caracteres.")
	private String formacao;

}