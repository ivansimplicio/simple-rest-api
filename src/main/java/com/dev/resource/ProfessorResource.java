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

import com.dev.domain.Professor;
import com.dev.dto.professor.ProfessorDTO;
import com.dev.dto.professor.ProfessorInputDTO;
import com.dev.dto.professor.ProfessorSaveDTO;
import com.dev.dto.usuario.UsuarioOutputDTO;
import com.dev.service.ProfessorService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "professores")
public class ProfessorResource {
	
	@Autowired
	private ProfessorService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PreAuthorize("hasAnyRole('PROFESSOR', 'COORDENADOR')")
	@ApiOperation("Buscar um professor por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProfessorDTO> find(@PathVariable Integer id){
		Professor obj = service.find(id);
		ProfessorDTO objDTO = modelMapper.map(obj, ProfessorDTO.class);
		objDTO.add(linkTo(ProfessorResource.class).slash(objDTO.getId()).withSelfRel());
		objDTO.add(linkTo(methodOn(ProfessorResource.class).findAll()).withRel("lista de professores"));
		return ResponseEntity.ok().body(objDTO);
	}
	
	@PreAuthorize("hasAnyRole('PROFESSOR', 'COORDENADOR')")
	@ApiOperation("Listar todos os professores")
	@GetMapping()
	public ResponseEntity<CollectionModel<UsuarioOutputDTO>> findAll(){
		List<Professor> list = service.findAll();
		List<UsuarioOutputDTO> listDTO = list.stream()
				.map(x -> modelMapper.map(x, UsuarioOutputDTO.class))
				.collect(Collectors.toList());
		listDTO.forEach(x -> {
			x.add(linkTo(methodOn(ProfessorResource.class).find(x.getId())).withSelfRel());
		});
		Link link = linkTo(ProfessorResource.class).withSelfRel();
		CollectionModel<UsuarioOutputDTO> collectionModel = CollectionModel.of(listDTO, link);
		
		return ResponseEntity.ok().body(collectionModel);
	}
	
	@ApiOperation("Cadastrar um novo professor")
	@PostMapping()
	public ResponseEntity<Void> insert(@Valid @RequestBody ProfessorSaveDTO obj){
		Professor objAux = modelMapper.map(obj, Professor.class);
		service.insert(objAux);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objAux.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('PROFESSOR')")
	@ApiOperation("Atualizar os dados de um professor")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody ProfessorInputDTO obj){
		Professor objAux = modelMapper.map(obj, Professor.class);
		objAux.setId(id);
		service.update(objAux);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('PROFESSSOR')")
	@ApiOperation("Excluir o cadastro de um professor")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}