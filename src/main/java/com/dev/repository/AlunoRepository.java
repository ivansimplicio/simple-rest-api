package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.domain.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {
	
	Aluno findByMatricula(String matricula);

}