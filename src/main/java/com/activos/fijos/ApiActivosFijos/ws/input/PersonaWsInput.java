package com.activos.fijos.ApiActivosFijos.ws.input;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaWsInput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -512459995511094687L;
	
	private int idPersona;
	private String nombre;
	private String identificacion;
	private boolean estado;
	private int area;

}
