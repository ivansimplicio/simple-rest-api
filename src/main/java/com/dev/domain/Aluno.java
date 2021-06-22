package com.dev.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dev.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "alunos")
@Getter
@Setter
@NoArgsConstructor
public class Aluno extends Usuario{
	
	public static final long serialVersionUID = 1L;
	
	@Column(nullable = false, unique = true)
	private String matricula;
	
	@Column(name = "nome_da_mae")
	private String nomeDaMae;
	
	@Column(name = "nome_do_pai")
	private String nomeDoPai;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_de_nascimento")
	private Date dataDeNascimento;
	
	@ManyToMany
	@JoinTable(	name = "ALUNOS_TURMAS",
				joinColumns = @JoinColumn(name = "aluno_id"),
				inverseJoinColumns = @JoinColumn(name = "turma_id"))
	private List<Turma> turmas = new ArrayList<>();
	
	public Aluno(Integer id, String nome, String CPF, String email, String senha, String matricula,
			String nomeDaMae, String nomeDoPai, Date dataDeNascimento) {
		super(id, nome, CPF, email, senha, Role.ALUNO);
		this.matricula = matricula;
		this.nomeDaMae = nomeDaMae;
		this.nomeDoPai = nomeDoPai;
		this.dataDeNascimento = dataDeNascimento;
	}
}