package com.activos.fijos.ApiActivosFijos.ws.input;

import java.io.Serializable;
import java.util.Date;

import com.activos.fijos.ApiActivosFijos.ws.output.ActivoFijoWsOutput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivoFijoWsInput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -656734814471723835L;
	private int id;
	private String nombre;
	private int tipoActivo;
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
