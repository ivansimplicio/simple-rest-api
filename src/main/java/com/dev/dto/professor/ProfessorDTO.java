package com.dev.dto.professor;

import java.util.ArrayList;
import java.util.List;

import com.dev.dto.turma.TurmaSimpleDTO;
import com.dev.dto.usuario.UsuarioOutputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO extends UsuarioOutputDTO{
	
	public static final long serialVersionUID = 1L;
	
	private Double salario;
	private String formacao;
	
	private List<TurmaSimpleDTO> turmas = new ArrayList<>();

}