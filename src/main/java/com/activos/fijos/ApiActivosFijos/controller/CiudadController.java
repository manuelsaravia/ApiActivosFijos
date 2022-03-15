package com.activos.fijos.ApiActivosFijos.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.activos.fijos.ApiActivosFijos.interfaces.IGestionarCiudad;
import com.activos.fijos.ApiActivosFijos.ws.input.CiudadWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {

	private static final Logger log = LoggerFactory.getLogger(CiudadController.class);
	
	@Autowired
	private IGestionarCiudad iCiudad;
	
	@GetMapping("/getCiudadId")
	public ResponseEntity<RespuestaGeneralWS> getCiudadId(@RequestParam int id) {
		return this.iCiudad.getCiudadId(id);
	}
	
	@GetMapping("/getCiudadNombre")
	public ResponseEntity<RespuestaGeneralWS> getCiudadNombre(@RequestParam String nombre) {
		return this.iCiudad.getCiudadNombre(nombre);
	}
	
	@GetMapping("/getCiudades")
	public ResponseEntity<Object> getCiudades() {
		return this.iCiudad.getCiudades();
	}
	
	@PostMapping("/setCiudadNombre")
	public ResponseEntity<RespuestaGeneralWS> setCiudadeNombre(@RequestBody CiudadWsInput input) {
		return this.iCiudad.setCiudadNombre(input);
	}
	
	@PostMapping("/setCiudadEstado")
	public ResponseEntity<RespuestaGeneralWS> setCiudadeEstado(@RequestBody CiudadWsInput input) {
		return this.iCiudad.setCiudadEstado(input);
	}
	
	@PostMapping("/addCiudad")
	public ResponseEntity<RespuestaGeneralWS> addCiudad(@RequestBody CiudadWsInput input) {
		return this.iCiudad.addCiudad(input);
	}
	
}
