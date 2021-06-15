package com.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dev.domain.Aluno;
import com.dev.repository.AlunoRepository;
import com.dev.service.exception.DataIntegrityException;
import com.dev.service.exception.ObjectNotFoundException;
import com.dev.service.interfaces.StandardCRUDOperations;

@Service
public class AlunoService implements StandardCRUDOperations<Aluno> {
	
	@Autowired
	private AlunoRepository repo;
	
	@Override
	public Aluno find(Integer id) {
		Optional<Aluno> obj = repo.findById(id);
		return obj.orElseThrow(() -> 
			new ObjectNotFoundException("Recurso não encontrado! Id: "+id+", Tipo: "+Aluno.class.getName()));
	}
	
	@Override
	public List<Aluno> findAll(){
		return repo.findAll();
	}
	
	@Override
	public Aluno insert(Aluno obj) {
		obj.setId(null);
		obj.setRole("ALUNO");
		return save(obj);
	}

	@Override
	public Aluno update(Aluno obj) {
		Aluno objAux = find(obj.getId());
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
	
	private Aluno save(Aluno obj) {
		try {
			obj = repo.save(obj);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível salvar o recurso.");
		}
		return obj;
	}

	private void updateData(Aluno objAux, Aluno obj) {
		BeanUtils.copyProperties(obj, objAux, "id", "senha", "role", "matricula", "turmas");
	}
}