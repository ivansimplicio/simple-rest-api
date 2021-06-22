package com.dev.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
	@ApiOperation("Buscar um aluno por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<AlunoDTO> find(@PathVariable Integer id){
		Aluno obj = service.find(id);
		AlunoDTO objDTO = modelMapper.map(obj, AlunoDTO.class);
		objDTO.add(linkTo(AlunoResource.class).slash(objDTO.getId()).withSelfRel());
		objDTO.add(linkTo(methodOn(AlunoResource.class).findAll()).withRel("lista de alunos"));
		return ResponseEntity.ok().body(objDTO);
	}
	
	@PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
	@ApiOperation("Listar todos os alunos")
	@GetMapping()
	public ResponseEntity<CollectionModel<UsuarioOutputDTO>> findAll(){
		List<Aluno> list = service.findAll();
		List<UsuarioOutputDTO> listDTO = list.stream()
				.map(x -> modelMapper.map(x, UsuarioOutputDTO.class))
				.collect(Collectors.toList());
		listDTO.forEach(x -> {
			x.add(linkTo(methodOn(AlunoResource.class).find(x.getId())).withSelfRel());
		});
		Link link = linkTo(AlunoResource.class).withSelfRel();
		CollectionModel<UsuarioOutputDTO> collectionModel = CollectionModel.of(listDTO, link);
		
		return ResponseEntity.ok().body(collectionModel);
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
	
	@PreAuthorize("hasAnyRole('ALUNO')")
	@ApiOperation("Atualizar os dados de um aluno")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody AlunoInputDTO obj){
		Aluno objAux = modelMapper.map(obj, Aluno.class);
		objAux.setId(id);
		service.update(objAux);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ALUNO')")
	@ApiOperation("Excluir o cadastro de um aluno")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}