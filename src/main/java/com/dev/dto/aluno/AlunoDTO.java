package com.dev.dto.aluno;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dev.dto.turma.TurmaSimpleDTO;
import com.dev.dto.usuario.UsuarioOutputDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataDeNascimento;
	
	private List<TurmaSimpleDTO> turmas = new ArrayList<>();

}