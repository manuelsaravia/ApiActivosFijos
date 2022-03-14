package com.activos.fijos.ApiActivosFijos.entity;

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
@Table(name = "activofijo")
public class ActivoFijo {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nombre", unique=true)
	private String nombre;
	
	@JoinColumn(name="tipoActivo", nullable=false)
	@ManyToOne(optional = false,  fetch = FetchType.LAZY)
	private TipoActivo tipoActivo;
	
	@Column(name="serial", unique=true)
	private String serial;
	
	@Column(name="numeroInternoInventario")
	private String numeroInternoInventario;
	
	@Column(name="peso")
	private float peso;
	
	@Column(name="alto")
	private float alto;
	
	@Column(name="ancho")
	private float ancho;
	
	@Column(name="largo")
	private float largo;
	
	@Column(name="valorCompra", nullable=false)
	private double valorCompra;
	
	@Column(name="fechaCompra", nullable=false)
	private Date fechaCompra;
	
	@Column(name="entregado")
	private int entregado;
	
	@Column(name="estado")
	private int estado;
	
}
