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

import com.dev.domain.Coordenador;
import com.dev.dto.coordenador.CoordenadorDTO;
import com.dev.dto.coordenador.CoordenadorSaveDTO;
import com.dev.dto.usuario.UsuarioInputDTO;
import com.dev.dto.usuario.UsuarioOutputDTO;
import com.dev.service.CoordenadorService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/coordenadores")
public class CoordenadorResource {
	
	@Autowired
	private CoordenadorService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PreAuthorize("hasAnyRole('COORDENADOR')")
	@ApiOperation("Buscar um coordenador por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<CoordenadorDTO> find(@PathVariable Integer id){
		Coordenador obj = service.find(id);
		CoordenadorDTO objDTO = modelMapper.map(obj, CoordenadorDTO.class);
		objDTO.add(linkTo(CoordenadorResource.class).slash(objDTO.getId()).withSelfRel());
		objDTO.add(linkTo(methodOn(CoordenadorResource.class).findAll()).withRel("lista de coordenadores"));
		return ResponseEntity.ok().body(objDTO);
	}
	
	@PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
	@ApiOperation("Listar todos os coordenadores")
	@GetMapping()
	public ResponseEntity<CollectionModel<UsuarioOutputDTO>> findAll(){
		List<Coordenador> list = service.findAll();
		List<UsuarioOutputDTO> listDTO = list.stream()
				.map(x -> modelMapper.map(x, UsuarioOutputDTO.class))
				.collect(Collectors.toList());
		listDTO.forEach(x -> {
			x.add(linkTo(methodOn(CoordenadorResource.class).find(x.getId())).withSelfRel());
		});
		Link link = linkTo(CoordenadorResource.class).withSelfRel();
		CollectionModel<UsuarioOutputDTO> collectionModel = CollectionModel.of(listDTO, link);
		return ResponseEntity.ok().body(collectionModel);
	}
	
	@PreAuthorize("hasAnyRole('COORDENADOR')")
	@ApiOperation("Cadastrar um novo coordenador")
	@PostMapping()
	public ResponseEntity<Void> insert(@RequestBody CoordenadorSaveDTO obj){
		Coordenador objAux = modelMapper.map(obj, Coordenador.class);
		service.insert(objAux);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objAux.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('COORDENADOR')")
	@ApiOperation("Atualizar os dados de um coordenador")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody UsuarioInputDTO obj){
		Coordenador objAux = modelMapper.map(obj, Coordenador.class);
		objAux.setId(id);
		service.update(objAux);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('COORDENADOR')")
	@ApiOperation("Excluir o cadastro de um coordenador")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}