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

import com.activos.fijos.ApiActivosFijos.interfaces.IGestionarArea;
import com.activos.fijos.ApiActivosFijos.ws.input.AreaWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

@RestController
@RequestMapping("/area")
public class AreaController {

	private static final Logger log = LoggerFactory.getLogger(CiudadController.class);
	
	@Autowired
	private IGestionarArea iArea;
	
	@GetMapping("/getAreaId")
	public ResponseEntity<RespuestaGeneralWS> getAreaId(@RequestParam int id) {
		return this.iArea.getAreaId(id);
	}
	
	@GetMapping("/getAreaNombre")
	public ResponseEntity<RespuestaGeneralWS> getAreaNombre(@RequestParam String nombre) {
		return this.iArea.getAreaNombre(nombre);
	}
	
	@GetMapping("/getAreas")
	public ResponseEntity<Object> getAreas() {
		return this.iArea.getAreas();
	}
	
	@PostMapping("/setAreaNombre")
	public ResponseEntity<RespuestaGeneralWS> setAreaNombre(@RequestBody AreaWsInput input) {
		return this.iArea.setAreaNombre(input);
	}
	
	@PostMapping("/setAreaEstado")
	public ResponseEntity<RespuestaGeneralWS> setAreaEstado(@RequestBody AreaWsInput input) {
		return this.iArea.setAreaEstado(input);
	}
	
	@PostMapping("/setAreaCiudad")
	public ResponseEntity<RespuestaGeneralWS> setAreaCiudad(@RequestBody AreaWsInput input) {
		return this.iArea.setAreaCiudad(input);
	}
	
	@PostMapping("/addArea")
	public ResponseEntity<RespuestaGeneralWS> addArea(@RequestBody AreaWsInput input) {
		return this.iArea.addArea(input);
	}
}
