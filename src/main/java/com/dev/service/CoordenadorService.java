package com.dev.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dev.domain.Coordenador;
import com.dev.repository.CoordenadorRepository;
import com.dev.service.exception.DataIntegrityException;
import com.dev.service.exception.ObjectNotFoundException;
import com.dev.service.interfaces.StandardCRUDOperations;

@Service
public class CoordenadorService implements StandardCRUDOperations<Coordenador>{
	
	@Autowired
	private CoordenadorRepository repo;

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
		obj.setRole("COORDENADOR");
		obj.setDataDeCadastro(new Date());
		return save(obj);
	}

	@Override
	public Coordenador update(Coordenador obj) {
		Coordenador objAux = find(obj.getId());
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