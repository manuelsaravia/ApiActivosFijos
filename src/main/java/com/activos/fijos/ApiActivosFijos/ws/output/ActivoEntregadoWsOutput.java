package com.activos.fijos.ApiActivosFijos.ws.output;

import java.io.Serializable;
import java.util.Date;

import com.activos.fijos.ApiActivosFijos.ws.input.ActivoEntregadoWsInput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivoEntregadoWsOutput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3701474278409325922L;
	private int id;
	private String tipoEntrega;
	private String nombreEntregado; //idArea o idPersona
	private Date fechaEntrega;
	private Date fechaRetiro;
	private ActivoFijoWsOutput activoFijo;

}
