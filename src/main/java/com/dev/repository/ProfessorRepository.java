package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.domain.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer>{

}