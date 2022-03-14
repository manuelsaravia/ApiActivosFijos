package com.activos.fijos.ApiActivosFijos.interfaces;

import org.springframework.http.ResponseEntity;

import com.activos.fijos.ApiActivosFijos.ws.input.PersonaWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

public interface IGestionarPersona {
	
public ResponseEntity<RespuestaGeneralWS> getPersonaId(int idPersona);
	
	public ResponseEntity<RespuestaGeneralWS> getPersonaNombre(String nombre);
	
	public ResponseEntity<RespuestaGeneralWS> getPersonaIdentificacion(String identificacion);
	
	public ResponseEntity<Object> getPersonas();
	
	public ResponseEntity<RespuestaGeneralWS> setPersonaNombre(PersonaWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> setPersonaIdentificacion(PersonaWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> setPersonaEstado(PersonaWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> setPersonaArea(PersonaWsInput input);
	
	public ResponseEntity<RespuestaGeneralWS> addPersona(PersonaWsInput input);

}
