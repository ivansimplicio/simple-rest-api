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

import com.dev.domain.Turma;
import com.dev.dto.turma.TurmaDTO;
import com.dev.dto.turma.TurmaSaveDTO;
import com.dev.dto.turma.TurmaSimpleDTO;
import com.dev.service.TurmaService;

@RestController
@RequestMapping(value = "/turmas")
public class TurmaResource {

	@Autowired
	private TurmaService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TurmaDTO> find(@PathVariable Integer id){
		Turma obj = service.find(id);
		TurmaDTO objDTO = modelMapper.map(obj, TurmaDTO.class);
		return ResponseEntity.ok().body(objDTO);
	}
	
	@GetMapping()
	public ResponseEntity<List<TurmaSimpleDTO>> findAll(){
		List<Turma> list = service.findAll();
		List<TurmaSimpleDTO> listDTO = list.stream()
					.map(x -> modelMapper.map(x, TurmaSimpleDTO.class))
					.collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody TurmaSaveDTO obj){
		Turma objAux = modelMapper.map(obj, Turma.class);
		service.insert(objAux);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(objAux.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody TurmaSaveDTO obj){
		Turma objAux = modelMapper.map(obj, Turma.class);
		objAux.setId(id);
		service.update(objAux);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}/matricular")
	public ResponseEntity<Void> matricular(@PathVariable Integer id, @RequestBody TurmaDTO obj){
		Turma objAux = modelMapper.map(obj, Turma.class);
		objAux.setId(id);
		service.matricular(objAux);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}/desmatricular")
	public ResponseEntity<Void> desmatricular(@PathVariable Integer id, @RequestBody TurmaDTO obj){
		Turma objAux = modelMapper.map(obj, Turma.class);
		objAux.setId(id);
		service.desmatricular(objAux);
		return ResponseEntity.noContent().build();
	}
}