package com.activos.fijos.ApiActivosFijos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.activos.fijos.ApiActivosFijos.entity.ActivoEntregadoArea;

public interface ActivoEntregadoAreaRepository extends JpaRepository<ActivoEntregadoArea, Integer> {
	
	@Query(value="SELECT * FROM ACTIVOENTREGADOAREA WHERE ACTIVO_FIJO=:idActivoFijo", nativeQuery = true)
	Optional<ActivoEntregadoArea> finddByActivoFijo(@Param("idActivoFijo") int id);
}
