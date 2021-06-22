package com.dev.dto.coordenador;

import java.util.Date;

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
public class CoordenadorDTO extends UsuarioOutputDTO{
	
	public static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataDeCadastro;

}