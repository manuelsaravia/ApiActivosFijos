package com.activos.fijos.ApiActivosFijos.ws.output;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaGeneralWS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3886191718387709513L;
	private String descripcion;
	private Object obj;
	

}
