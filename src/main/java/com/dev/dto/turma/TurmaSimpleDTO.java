package com.dev.dto.turma;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurmaSimpleDTO implements Serializable{
	
	public static final long serialVersionUID = 1l;
	
	private Integer id;
	private String disciplina;

}