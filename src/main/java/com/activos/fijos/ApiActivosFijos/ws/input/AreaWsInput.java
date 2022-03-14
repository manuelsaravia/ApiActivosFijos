package com.activos.fijos.ApiActivosFijos.ws.input;

import java.io.Serializable;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaWsInput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -823652058317230470L;
	private int idArea;
	private String nombre;
	private int idCiudad;
	private boolean estado;
	

}
