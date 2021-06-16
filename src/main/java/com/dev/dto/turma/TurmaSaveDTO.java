package com.dev.dto.turma;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.dev.domain.Professor;
import com.dev.service.validation.TurmaInsert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@TurmaInsert
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TurmaSaveDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message = "Preenchimento obrigatório.")
	@Length(min = 5, max = 25, message = "O campo deve conter entre 5 e 25 caracteres.")
	private String disciplina;
	
	private Professor professor;

}