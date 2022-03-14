package com.activos.fijos.ApiActivosFijos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activos.fijos.ApiActivosFijos.interfaces.IGestionarActivoFijo;
import com.activos.fijos.ApiActivosFijos.ws.input.ActivoFijoWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

@RestController
@RequestMapping("/activo")
public class ActivoFijoController {
	
	@Autowired
	private IGestionarActivoFijo iActivo;
	
	@GetMapping("getActivoFijo")
	public ResponseEntity<RespuestaGeneralWS> getActivoFijo(@RequestBody ActivoFijoWsInput input){
		return this.iActivo.getActivoFijo(input);
	}
	
	@GetMapping("getActivosFijos")
	public ResponseEntity<Object> getActivoFijos(){
		return this.iActivo.getActivoFijos();
	}
	
	@PostMapping("setActivoFijo")
	public ResponseEntity<RespuestaGeneralWS> setActivoFijo(@RequestBody ActivoFijoWsInput input){
		return this.iActivo.setActivoFijo(input);
	}
	
	@PostMapping("setActivoFijoTipo")
	public ResponseEntity<RespuestaGeneralWS> setActivoFijoTipo(@RequestBody ActivoFijoWsInput input){
		return this.iActivo.setActivoFijoTipo(input);
	}
	
	@PostMapping("addActivoFijo")
	public ResponseEntity<RespuestaGeneralWS> addActivoFijo(@RequestBody ActivoFijoWsInput input){
		return this.iActivo.addActivoFijo(input);
	}
	
	

}
