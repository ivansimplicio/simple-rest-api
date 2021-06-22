package com.dev.domain.enums;

public enum Role {
	
	COORDENADOR(1, "ROLE_COORDENADOR"),
	PROFESSOR(2, "ROLE_PROFESSOR"),
	ALUNO(3, "ROLE_ALUNO");

	private int cod;
	private String descricao;

	private Role(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Role toEnum(Integer cod) {

		if(cod == null) {
			return null;
		}

		for(Role ep : Role.values()) {
			if(cod.equals(ep.getCod())) {
				return ep;
			}
		}
		throw new IllegalArgumentException("Id inválido: "+cod);
	}
}