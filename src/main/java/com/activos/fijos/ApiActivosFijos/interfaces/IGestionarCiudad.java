package com.activos.fijos.ApiActivosFijos.interfaces;


import org.springframework.http.ResponseEntity;

import com.activos.fijos.ApiActivosFijos.ws.input.CiudadWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;


public interface IGestionarCiudad {
	
	public ResponseEntity<RespuestaGeneralWS> getCiudadId(int idCiudad);
	
	public ResponseEntity<RespuestaGeneralWS> getCiudadNombre(String nombre);
	
	public ResponseEntity<Object> getCiudades();
	
	public ResponseEntity<RespuestaGeneralWS> setCiudadNombre(CiudadWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> setCiudadEstado(CiudadWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> addCiudad(CiudadWsInput input);

}
