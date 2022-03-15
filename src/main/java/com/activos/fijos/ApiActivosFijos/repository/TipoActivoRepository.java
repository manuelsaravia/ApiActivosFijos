package com.activos.fijos.ApiActivosFijos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.activos.fijos.ApiActivosFijos.entity.TipoActivo;

@Repository
public interface TipoActivoRepository extends JpaRepository<TipoActivo, Integer> {

}
