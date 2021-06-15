package com.dev.dto.aluno;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.dev.dto.usuario.UsuarioInputDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoInputDTO extends UsuarioInputDTO{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 8, max = 30, message = "O campo deve conter entre 8 e 30 caracteres.")
	private String nomeDaMae;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 8, max = 30, message = "O campo deve conter entre 8 e 30 caracteres.")
	private String nomeDoPai;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataDeNascimento;

}