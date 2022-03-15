package com.activos.fijos.ApiActivosFijos.ws.output;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaWsOutput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5298502377118172308L;
	
	private int idArea;
	private String nombre;
	private CiudadWsOutput ciudad;
	private boolean estado;

}
