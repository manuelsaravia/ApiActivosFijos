package com.activos.fijos.ApiActivosFijos.ws.output;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivoFijoWsOutput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5798766181563348186L;
	private int id;
	private String nombre;
	private String tipoActivo;
	private String serial;
	private String numeroInternoInventario;
	private float peso;
	private float alto;
	private float ancho;
	private float largo;
	private double valorcompra;
	private Date fechaCompra;
	private boolean entregado;
	private boolean estado;

}
