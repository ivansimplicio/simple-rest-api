package com.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.domain.Coordenador;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Integer>{

}