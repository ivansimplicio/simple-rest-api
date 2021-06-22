package com.dev.dto.coordenador;

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
public class CoordenadorSaveDTO extends UsuarioInputDTO{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 6, max = 20, message = "O campo deve conter entre 6 e 20 caracteres.")
	private String senha;
}