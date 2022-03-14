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

import com.activos.fijos.ApiActivosFijos.interfaces.IGestionarPersona;
import com.activos.fijos.ApiActivosFijos.ws.input.PersonaWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

@RestController
@RequestMapping("/persona")
public class PersonController {
	
private static final Logger log = LoggerFactory.getLogger(CiudadController.class);
	
	@Autowired 
	private IGestionarPersona iPersona;

	@GetMapping("/getPersonaId")
	public ResponseEntity<RespuestaGeneralWS> getPersonaId(@RequestParam int idPersona) {
		return this.iPersona.getPersonaId(idPersona);
	}

	@GetMapping("/getPersonaNombre")
	public ResponseEntity<RespuestaGeneralWS> getPersonaNombre(@RequestParam String nombre) {
		return this.iPersona.getPersonaNombre(nombre);
	}

	@GetMapping("/getPersonaIdentificacion")
	public ResponseEntity<RespuestaGeneralWS> getPersonaIdentificacion(@RequestParam String identificacion) {
		return this.iPersona.getPersonaIdentificacion(identificacion);
	}

	@GetMapping("/getPersonas")
	public ResponseEntity<Object> getPersonas() {
		return this.getPersonas();
	}

	@PostMapping("/setPersonaNombre")
	public ResponseEntity<RespuestaGeneralWS> setPersonaNombre(@RequestBody PersonaWsInput input) {
		return this.iPersona.setPersonaNombre(input);
	}

	@PostMapping("/setPersonaIdentificacion")
	public ResponseEntity<RespuestaGeneralWS> setPersonaIdentificacion(@RequestBody PersonaWsInput input) {
		return this.iPersona.setPersonaIdentificacion(input);
	}

	@PostMapping("/setPersonaEstado")
	public ResponseEntity<RespuestaGeneralWS> setPersonaEstado(@RequestBody PersonaWsInput input) {
		return this.iPersona.setPersonaEstado(input);
	}

	@PostMapping("/setPersonaArea")
	public ResponseEntity<RespuestaGeneralWS> setPersonaArea(@RequestBody PersonaWsInput input) {
		return this.iPersona.setPersonaArea(input);
	}

	@PostMapping("/addPersona")
	public ResponseEntity<RespuestaGeneralWS> addPersona(@RequestBody PersonaWsInput input) {
		return this.iPersona.addPersona(input);
	}

}
