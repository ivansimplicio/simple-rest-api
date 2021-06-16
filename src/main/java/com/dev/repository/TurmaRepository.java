package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.domain.Turma;

@Repository
public interface TurmaRepository extends JpaRepository<Turma, Integer>{

	Turma findByDisciplina(String disciplina);
}