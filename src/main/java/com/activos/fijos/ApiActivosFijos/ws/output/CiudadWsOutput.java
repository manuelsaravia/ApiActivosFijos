package com.activos.fijos.ApiActivosFijos.ws.output;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CiudadWsOutput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 241840370461994096L;
	
	private int id;
	private String nombre;
	private boolean estado;

}
