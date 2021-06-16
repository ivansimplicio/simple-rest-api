package com.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dev.domain.Professor;
import com.dev.repository.ProfessorRepository;
import com.dev.service.exception.DataIntegrityException;
import com.dev.service.exception.ObjectNotFoundException;
import com.dev.service.interfaces.StandardCRUDOperations;

@Service
public class ProfessorService implements StandardCRUDOperations<Professor>{

	@Autowired
	private ProfessorRepository repo;
	
	@Override
	public Professor find(Integer id) {
		Optional<Professor> obj = repo.findById(id);
		return obj.orElseThrow(() ->
			new ObjectNotFoundException("Recurso não encontrado! Id: "+id+", Tipo: "+Professor.class.getName()));
	}

	@Override
	public List<Professor> findAll() {
		return repo.findAll();
	}

	@Override
	public Professor insert(Professor obj) {
		obj.setId(null);
		obj.setRole("PROFESSOR");
		return save(obj);
	}

	@Override
	public Professor update(Professor obj) {
		Professor objAux = find(obj.getId());
		updateData(objAux, obj);
		return save(objAux);
	}

	@Override
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível deletar o recurso.");
		}
	}

	private Professor save(Professor obj) {
		try {
			obj = repo.save(obj);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível salvar o recurso.");
		}
		return obj;
	}

	private void updateData(Professor objAux, Professor obj) {
		BeanUtils.copyProperties(obj, objAux, "id", "senha", "role", "turmas");
	}
}