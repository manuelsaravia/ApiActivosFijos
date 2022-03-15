package com.activos.fijos.ApiActivosFijos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.activos.fijos.ApiActivosFijos.entity.ActivoEntregadoArea;
import com.activos.fijos.ApiActivosFijos.entity.ActivoEntregadoPersona;

@Repository
public interface ActivoEntregadoPersonaRepository extends JpaRepository<ActivoEntregadoPersona, Integer> {

	@Query(value="SELECT * FROM ACTIVOENTREGADOPERSONA WHERE ACTIVO_FIJO=:idActivoFijo", nativeQuery = true)
	Optional<ActivoEntregadoPersona> finddByActivoFijo(@Param("idActivoFijo") int id);
}
