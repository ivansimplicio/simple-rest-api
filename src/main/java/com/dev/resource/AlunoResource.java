package com.dev.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dev.domain.Aluno;
import com.dev.dto.aluno.AlunoDTO;
import com.dev.dto.aluno.AlunoInputDTO;
import com.dev.dto.aluno.AlunoSaveDTO;
import com.dev.dto.usuario.UsuarioOutputDTO;
import com.dev.service.AlunoService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/alunos")
public class AlunoResource {
	
	@Autowired
	private AlunoService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@ApiOperation("Buscar um aluno por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<AlunoDTO> find(@PathVariable Integer id){
		Aluno obj = service.find(id);
		AlunoDTO objDTO = modelMapper.map(obj, AlunoDTO.class);
		return ResponseEntity.ok().body(objDTO);
	}
	
	@ApiOperation("Listar todos os alunos")
	@GetMapping()
	public ResponseEntity<List<UsuarioOutputDTO>> findAll(){
		List<Aluno> list = service.findAll();
		List<UsuarioOutputDTO> listDTO = list.stream()
				.map(x -> modelMapper.map(x, UsuarioOutputDTO.class))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@ApiOperation("Cadastrar um novo aluno")
	@PostMapping()
	public ResponseEntity<Void> insert(@Valid @RequestBody AlunoSaveDTO obj){
		Aluno objAux = modelMapper.map(obj, Aluno.class);
		service.insert(objAux);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objAux.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation("Atualizar os dados de um aluno")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody AlunoInputDTO obj){
		Aluno objAux = modelMapper.map(obj, Aluno.class);
		objAux.setId(id);
		service.update(objAux);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation("Excluir o cadastro de um aluno")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}