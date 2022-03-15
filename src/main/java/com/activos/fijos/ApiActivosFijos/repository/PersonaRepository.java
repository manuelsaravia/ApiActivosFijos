package com.activos.fijos.ApiActivosFijos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.activos.fijos.ApiActivosFijos.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
	
	Optional<Persona> findByNombre(String nombre);
	
	Optional<Persona> findByIdentificacion(String identificacion);

}
