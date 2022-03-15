package com.activos.fijos.ApiActivosFijos.interfaces;

import org.springframework.http.ResponseEntity;

import com.activos.fijos.ApiActivosFijos.ws.input.ActivoEntregadoWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

public interface IGestionarActivoEntregado {
	
	public ResponseEntity<RespuestaGeneralWS> getActivoEntregado(int id);
	
	public ResponseEntity<RespuestaGeneralWS> setActivoEntregado(ActivoEntregadoWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> addActivoEntregado(ActivoEntregadoWsInput input);

}
