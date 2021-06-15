package com.dev.dto.aluno;

import java.util.Date;

import com.dev.dto.usuario.UsuarioOutputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO extends UsuarioOutputDTO{

	private static final long serialVersionUID = 1L;
	
	private String matricula;
	private String nomeDaMae;
	private String nomeDoPai;
	private Date dataDeNascimento;
	
	//adicionar lista de turmas

}