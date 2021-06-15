package com.dev.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "admins")
@Getter
@Setter
@NoArgsConstructor
public class Admin extends Usuario{
	
	public static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Column(name = "data_de_cadastro")
	private Date dataDeCadastro;

	public Admin(Integer id, String nome, String CPF, String email, String senha, Date dataDeCadastro) {
		super(id, nome, CPF, email, senha, "ADMIN");
		this.dataDeCadastro = dataDeCadastro;
	}
}