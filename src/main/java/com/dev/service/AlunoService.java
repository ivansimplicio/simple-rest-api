package com.dev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.domain.Aluno;
import com.dev.domain.enums.Role;
import com.dev.repository.AlunoRepository;
import com.dev.security.UserSS;
import com.dev.service.exception.AuthorizationException;
import com.dev.service.exception.DataIntegrityException;
import com.dev.service.exception.ObjectNotFoundException;
import com.dev.service.interfaces.StandardCRUDOperations;

@Service
public class AlunoService implements StandardCRUDOperations<Aluno> {
	
	@Autowired
	private AlunoRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Override
	public Aluno find(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || user.hasRole(Role.ALUNO) && !user.getId().equals(id)) {
			throw new AuthorizationException("Acesso negado");
		}
		
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
		obj.setSenha(pe.encode(obj.getSenha()));
		obj.setRole(Role.ALUNO);
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