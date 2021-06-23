package com.dev.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.domain.Coordenador;
import com.dev.domain.enums.Role;
import com.dev.repository.CoordenadorRepository;
import com.dev.security.UserSS;
import com.dev.service.exception.AuthorizationException;
import com.dev.service.exception.DataIntegrityException;
import com.dev.service.exception.ObjectNotFoundException;
import com.dev.service.interfaces.StandardCRUDOperations;

@Service
public class CoordenadorService implements StandardCRUDOperations<Coordenador>{
	
	@Autowired
	private CoordenadorRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder pe;

	@Override
	public Coordenador find(Integer id) {
		Optional<Coordenador> obj = repo.findById(id);
		return obj.orElseThrow(() -> 
				new ObjectNotFoundException("Recurso não encontrado! Id: "+id+", Tipo: "+Coordenador.class.getName()));
	}

	@Override
	public List<Coordenador> findAll() {
		return repo.findAll();
	}

	@Override
	public Coordenador insert(Coordenador obj) {
		obj.setId(null);
		obj.setSenha(pe.encode(obj.getSenha()));
		obj.setRole(Role.COORDENADOR);
		obj.setDataDeCadastro(new Date());
		return save(obj);
	}

	@Override
	public Coordenador update(Coordenador obj) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.getId().equals(obj.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Coordenador objAux = find(obj.getId());
		updateData(objAux, obj);
		return save(objAux);
	}

	@Override
	public void delete(Integer id) {
		
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.getId().equals(id)) {
			throw new AuthorizationException("Acesso negado");
		}
		
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível deletar o recurso.");
		}
	}

	private Coordenador save(Coordenador obj) {
		try {
			obj = repo.save(obj);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível salvar o recurso.");
		}
		return obj;
	}

	private void updateData(Coordenador objAux, Coordenador obj) {
		BeanUtils.copyProperties(obj, objAux, "id", "senha", "role", "dataDeCadastro");
	}
}