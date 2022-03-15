package com.activos.fijos.ApiActivosFijos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.activos.fijos.ApiActivosFijos.entity.TipoEntrega;

@Repository
public interface TipoEntregaRepository extends JpaRepository<TipoEntrega, Integer> {

}
