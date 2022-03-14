package com.activos.fijos.ApiActivosFijos.ws.input;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CiudadWsInput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1158831986101707513L;
	
	private int idCiudad;
	private String nombreCiudad;
	private boolean estado;

}
