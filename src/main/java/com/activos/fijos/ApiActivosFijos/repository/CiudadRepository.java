package com.activos.fijos.ApiActivosFijos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.activos.fijos.ApiActivosFijos.entity.Ciudad;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer>{
	
	Optional<Ciudad> findByNombre(String nombre);

}
