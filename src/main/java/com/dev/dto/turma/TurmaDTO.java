package com.dev.dto.turma;

import java.util.ArrayList;
import java.util.List;

import com.dev.dto.usuario.UsuarioOutputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurmaDTO extends TurmaSimpleDTO{

	private static final long serialVersionUID = 1L;
	
	private UsuarioOutputDTO professor;
	
	private List<UsuarioOutputDTO> alunos = new ArrayList<>();

}