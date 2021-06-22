package com.dev.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.dev.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "coordenadores")
@Getter
@Setter
@NoArgsConstructor
public class Coordenador extends Usuario{
	
	public static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_de_cadastro")
	private Date dataDeCadastro;

	public Coordenador(Integer id, String nome, String CPF, String email, String senha, Date dataDeCadastro) {
		super(id, nome, CPF, email, senha, Role.COORDENADOR);
		this.dataDeCadastro = dataDeCadastro;
	}
}