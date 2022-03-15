package com.activos.fijos.ApiActivosFijos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.activos.fijos.ApiActivosFijos.interfaces.IGestionarActivoEntregado;
import com.activos.fijos.ApiActivosFijos.ws.input.ActivoEntregadoWsInput;
import com.activos.fijos.ApiActivosFijos.ws.input.ActivoFijoWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

@RestController
@RequestMapping("/entregado")
public class ActivoEntregadoController {
	
	@Autowired
	private IGestionarActivoEntregado iEntregado;
	
	@PostMapping("addEntregado")
	public  ResponseEntity<RespuestaGeneralWS> addEntregado(@RequestBody ActivoEntregadoWsInput input){
		return this.iEntregado.addActivoEntregado(input);
	}
	
	@GetMapping("getEntregado")
	public  ResponseEntity<RespuestaGeneralWS> addEntregado(@RequestParam int id){
		return this.iEntregado.getActivoEntregado(id);
	}
}
