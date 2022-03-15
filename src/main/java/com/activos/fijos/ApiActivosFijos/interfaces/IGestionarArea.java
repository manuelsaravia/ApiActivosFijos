package com.activos.fijos.ApiActivosFijos.interfaces;

import org.springframework.http.ResponseEntity;

import com.activos.fijos.ApiActivosFijos.ws.input.AreaWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;


public interface IGestionarArea {

	public ResponseEntity<RespuestaGeneralWS> getAreaId(int idArea);
	
	public ResponseEntity<RespuestaGeneralWS> getAreaNombre(String nombre);
	
	public ResponseEntity<Object> getAreas();
	
	public ResponseEntity<RespuestaGeneralWS> setAreaNombre(AreaWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> setAreaEstado(AreaWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> setAreaCiudad(AreaWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> addArea(AreaWsInput input);
}
