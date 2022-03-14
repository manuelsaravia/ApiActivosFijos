package com.activos.fijos.ApiActivosFijos.entity;

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
@Table(name = "area")
public class Area {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="nombre", nullable=false, unique=true)
	private String nombre;
	
	@JoinColumn(name = "ciudad", nullable = false)
	@ManyToOne(optional = false,  fetch = FetchType.LAZY)
	private Ciudad ciudad;
	
	@Column(name="estado")
	private int estado;
}
