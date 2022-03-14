package com.activos.fijos.ApiActivosFijos.ws.output;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonaWsOutput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7018496337334689398L;
	private int idPersona;
	private String nombre;
	private String identificacion;
	private boolean estado;
	private AreaWsOutput area;
}
