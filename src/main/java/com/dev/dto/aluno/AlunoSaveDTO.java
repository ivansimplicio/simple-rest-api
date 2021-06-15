package com.dev.dto.aluno;

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
public class AlunoSaveDTO extends AlunoInputDTO{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 6, max = 20, message = "O campo deve conter entre 6 e 20 caracteres.")
	private String senha;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 8, max = 8, message = "O campo deve conter 8 caracteres.")
	private String matricula;

}