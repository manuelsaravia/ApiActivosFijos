package com.activos.fijos.ApiActivosFijos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.activos.fijos.ApiActivosFijos.entity.ActivoFijo;
import com.activos.fijos.ApiActivosFijos.entity.TipoActivo;

@Repository
public interface ActivoFijoRepository extends JpaRepository<ActivoFijo, Integer> {
	
	Optional<ActivoFijo> findByNombre(String nombre);
	
	Optional<ActivoFijo> findBySerial(String serial);
	
	Optional<ActivoFijo> findByNumeroInternoInventario(String numeroInternoInventario);
	

}
