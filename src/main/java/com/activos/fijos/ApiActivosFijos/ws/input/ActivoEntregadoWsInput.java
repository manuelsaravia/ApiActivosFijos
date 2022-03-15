package com.activos.fijos.ApiActivosFijos.ws.input;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivoEntregadoWsInput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3754669929945796223L;
	private int id;
	private int tipoEntrega;
	private int idEntregado; //idArea o idPersona
	private Date fechaEntrega;
	private Date fechaRetiro;
	private int activoFijo;

}
