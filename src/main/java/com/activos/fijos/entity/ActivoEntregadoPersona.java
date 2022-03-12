package com.activos.fijos.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activoentregadopersona")
public class ActivoEntregadoPersona {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name="fk_tipo_entrega_persona", nullable=false)
	@ManyToOne(optional = false,  fetch = FetchType.LAZY)
	private TipoEntrega tipoEntrega;
	
	@JoinColumn(name="fk_entrega_persona", nullable=false)
	@ManyToOne(optional = false,  fetch = FetchType.LAZY)
	private Persona idEntregado;
	
	@Column(name="fechaEntrega", nullable=false, unique=true)
	private Date fechaEntrega;
	
	@Column(name = "fechaRetiro")
	private Date fechaRetiro;
	
	@JoinColumn(name="fk_activo_fijo_persona", nullable=false)
	@ManyToOne(optional = false,  fetch = FetchType.LAZY)
	private ActivoFijo activoFijo;

}
