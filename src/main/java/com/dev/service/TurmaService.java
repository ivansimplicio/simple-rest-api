package com.dev.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dev.domain.Aluno;
import com.dev.domain.Turma;
import com.dev.domain.enums.Role;
import com.dev.repository.AlunoRepository;
import com.dev.repository.TurmaRepository;
import com.dev.security.UserSS;
import com.dev.service.exception.AuthorizationException;
import com.dev.service.exception.DataIntegrityException;
import com.dev.service.exception.ObjectNotFoundException;
import com.dev.service.interfaces.StandardCRUDOperations;

@Service
public class TurmaService implements StandardCRUDOperations<Turma>{
	
	@Autowired
	private TurmaRepository repo;
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private AlunoService alunoService;
	
	@Override
	public Turma find(Integer id) {
		
		UserSS user = UserService.authenticated();
		Optional<Turma> obj = repo.findById(id);
		if(obj != null) {
			if(user == null || !user.hasRole(Role.COORDENADOR) && !user.getId().equals(obj.get().getProfessor().getId())) {
				throw new AuthorizationException("Acesso negado");
			}
		}
		return obj.orElseThrow(() ->
				new ObjectNotFoundException("Recurso não encontrado! Id: "+id+", Tipo: "+Turma.class.getName()));
	}
	
	@Override
	public List<Turma> findAll(){
		return repo.findAll();
	}
	
	@Override
	public Turma insert(Turma obj) {
		obj.setId(null);
		professorService.find(obj.getProfessor().getId());
		return save(obj);
	}
	
	@Override
	public Turma update(Turma obj) {
		Turma objAux = find(obj.getId());
		updateData(objAux, obj);
		professorService.find(objAux.getProfessor().getId());
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
	
	public void matricular(Turma obj) {
		Turma objAux = find(obj.getId());
		List<Aluno> list = new ArrayList<>();
		for(Aluno a : obj.getAlunos()) {
			Aluno aux = alunoService.find(a.getId());
			if(objAux.getAlunos().contains(aux)) {
				throw new DataIntegrityException("O aluno com o Id: "+aux.getId()+" já está cadastrado na Turma.");
			}
			objAux.getAlunos().add(aux);
			aux.getTurmas().add(objAux);
			list.add(aux);
		}
		alunoRepository.saveAll(list);
		repo.save(objAux);
	}
	
	public void desmatricular(Turma obj) {
		Turma objAux = find(obj.getId());
		List<Aluno> list = new ArrayList<>();
		for(Aluno a : obj.getAlunos()) {
			Aluno aux = alunoService.find(a.getId());
			if(!objAux.getAlunos().contains(aux)) {
				throw new DataIntegrityException("O aluno com o Id: "+aux.getId()+" não está cadastrado na Turma.");
			}
			objAux.getAlunos().remove(aux);
			aux.getTurmas().remove(objAux);
			list.add(aux);
		}
		alunoRepository.saveAll(list);
		repo.save(objAux);	
	}

	private Turma save(Turma obj) {
		try {
			obj = repo.save(obj);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não foi possível salvar o recurso.");
		}
		return obj;
	}
	
	private void updateData(Turma objAux, Turma obj) {
		BeanUtils.copyProperties(obj, objAux, "id", "alunos");
	}
}