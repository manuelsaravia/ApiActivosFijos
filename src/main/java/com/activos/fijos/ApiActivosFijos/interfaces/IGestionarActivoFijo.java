package com.activos.fijos.ApiActivosFijos.interfaces;

import java.util.Date;

import org.springframework.http.ResponseEntity;

import com.activos.fijos.ApiActivosFijos.ws.input.ActivoFijoWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

public interface IGestionarActivoFijo {
		
	public ResponseEntity<RespuestaGeneralWS> getActivoFijo(ActivoFijoWsInput input);
	
	public ResponseEntity<Object> getActivoFijos();
	
	public ResponseEntity<Object> getActivoFijosXFechaCompra(String fechaCompra);
	
	public ResponseEntity<Object> getActivoFijosXTipoActivo(int tipo);
	
	public ResponseEntity<RespuestaGeneralWS> setActivoFijo(ActivoFijoWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> setActivoFijoTipo(ActivoFijoWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> addActivoFijo(ActivoFijoWsInput input);

}
