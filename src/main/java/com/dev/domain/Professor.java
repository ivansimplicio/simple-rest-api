package com.dev.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.dev.domain.enums.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "professor")
@Getter
@Setter
@NoArgsConstructor
public class Professor extends Usuario {

	public static final long serialVersionUID = 1L;
	
	private Double salario;
	
	@Column(nullable = false)
	private String formacao;
	
	@OneToMany(mappedBy = "professor")
	private List<Turma> turmas = new ArrayList<>();
	
	public Professor(Integer id, String nome, String CPF, String email, String senha,
			Double salario, String formacao) {
		super(id, nome, CPF, email, senha, Role.PROFESSOR);
		this.salario = salario;
		this.formacao = formacao;
	}
}