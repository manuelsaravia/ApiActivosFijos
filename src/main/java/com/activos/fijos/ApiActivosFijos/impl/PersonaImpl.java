package com.activos.fijos.ApiActivosFijos.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.activos.fijos.ApiActivosFijos.entity.Area;
import com.activos.fijos.ApiActivosFijos.entity.Ciudad;
import com.activos.fijos.ApiActivosFijos.entity.Persona;
import com.activos.fijos.ApiActivosFijos.interfaces.IGestionarPersona;
import com.activos.fijos.ApiActivosFijos.repository.AreaRepository;
import com.activos.fijos.ApiActivosFijos.repository.PersonaRepository;
import com.activos.fijos.ApiActivosFijos.ws.input.PersonaWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.AreaWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.CiudadWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.PersonaWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

@Service
public class PersonaImpl implements IGestionarPersona {
	
	private static final Logger log = LoggerFactory.getLogger(PersonaImpl.class);
	
	@Autowired
	private PersonaRepository repository;
	
	@Autowired
	private AreaRepository areaRepository;

	@Override
	public ResponseEntity<RespuestaGeneralWS> getPersonaId(int idPersona) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(idPersona < 0) {
				log.info("id de persona inferior a 0");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Id Persona no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando area por id");
			Optional<Persona> persona = this.repository.findById(idPersona);
			if(!persona.isPresent()) {
				log.info("Persona no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			PersonaWsOutput output = new PersonaWsOutput();
			output.setIdPersona(persona.get().getId());
			output.setNombre(persona.get().getNombre().toUpperCase());
			output.setIdentificacion(persona.get().getIdentificacion());
			output.setEstado(persona.get().getEstado()==1?true:false);
			output.setArea(new AreaWsOutput());
			output.getArea().setIdArea(persona.get().getArea().getId());
			output.getArea().setNombre(persona.get().getArea().getNombre().toUpperCase());
			output.getArea().setEstado(persona.get().getEstado()==1?true:false);
			output.getArea().setCiudad(new CiudadWsOutput());
			output.getArea().getCiudad().setId(persona.get().getArea().getCiudad().getId());
			output.getArea().getCiudad().setNombre(persona.get().getArea().getCiudad().getNombre());
			output.getArea().getCiudad().setEstado(persona.get().getArea().getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Consulta Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> getPersonaNombre(String nombre) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(nombre == null || nombre.isEmpty()) {
				log.info("Nombre de persona no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Nombre persona no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando persona por nombre");
			Optional<Persona> persona = this.repository.findByNombre(nombre.toUpperCase());
			if(!persona.isPresent()) {
				log.info("Persona no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			PersonaWsOutput output = new PersonaWsOutput();
			output.setIdPersona(persona.get().getId());
			output.setNombre(persona.get().getNombre().toUpperCase());
			output.setIdentificacion(persona.get().getIdentificacion());
			output.setEstado(persona.get().getEstado()==1?true:false);
			output.setArea(new AreaWsOutput());
			output.getArea().setIdArea(persona.get().getArea().getId());
			output.getArea().setNombre(persona.get().getArea().getNombre().toUpperCase());
			output.getArea().setEstado(persona.get().getEstado()==1?true:false);
			output.getArea().setCiudad(new CiudadWsOutput());
			output.getArea().getCiudad().setId(persona.get().getArea().getCiudad().getId());
			output.getArea().getCiudad().setNombre(persona.get().getArea().getCiudad().getNombre());
			output.getArea().getCiudad().setEstado(persona.get().getArea().getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Consulta Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
			
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> getPersonaIdentificacion(String identificacion) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(identificacion == null || identificacion.isEmpty()) {
				log.info("identificacion de persona no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("identificacion persona no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando persona por identificacion");
			Optional<Persona> persona = this.repository.findByIdentificacion(identificacion);
			if(!persona.isPresent()) {
				log.info("Persona no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			PersonaWsOutput output = new PersonaWsOutput();
			output.setIdPersona(persona.get().getId());
			output.setNombre(persona.get().getNombre().toUpperCase());
			output.setIdentificacion(persona.get().getIdentificacion());
			output.setEstado(persona.get().getEstado()==1?true:false);
			output.setArea(new AreaWsOutput());
			output.getArea().setIdArea(persona.get().getArea().getId());
			output.getArea().setNombre(persona.get().getArea().getNombre().toUpperCase());
			output.getArea().setEstado(persona.get().getEstado()==1?true:false);
			output.getArea().setCiudad(new CiudadWsOutput());
			output.getArea().getCiudad().setId(persona.get().getArea().getCiudad().getId());
			output.getArea().getCiudad().setNombre(persona.get().getArea().getCiudad().getNombre());
			output.getArea().getCiudad().setEstado(persona.get().getArea().getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Consulta Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<Object> getPersonas() {
		ResponseEntity<Object> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			log.info("Listando areas");
			List<Persona> personas = this.repository.findAll();
			if(personas == null || personas.isEmpty()) {
				log.info("Listado de personas vacio");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			List<PersonaWsOutput> listadoPersona = new ArrayList<>();
			log.info("Recorriendo personas");
			for(Persona persona: personas) {
				PersonaWsOutput output = new PersonaWsOutput();
				output.setIdPersona(persona.getId());
				output.setNombre(persona.getNombre().toUpperCase());
				output.setIdentificacion(persona.getIdentificacion());
				output.setEstado(persona.getEstado()==1?true:false);
				output.setArea(new AreaWsOutput());
				output.getArea().setIdArea(persona.getArea().getId());
				output.getArea().setNombre(persona.getArea().getNombre().toUpperCase());
				output.getArea().setEstado(persona.getEstado()==1?true:false);
				output.getArea().setCiudad(new CiudadWsOutput());
				output.getArea().getCiudad().setId(persona.getArea().getCiudad().getId());
				output.getArea().getCiudad().setNombre(persona.getArea().getCiudad().getNombre());
				output.getArea().getCiudad().setEstado(persona.getArea().getCiudad().getEstado()==1?true:false);
				
				listadoPersona.add(output);
			}
			
			log.info("Preparando respuesta");
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Consulta Exitosa");
			respuestaDto.setObj(listadoPersona);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> setPersonaNombre(PersonaWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getIdPersona() < 0 || input.getNombre() == null || input.getNombre().isEmpty()) {
				log.info("Id persona o Nombre persona no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos persona no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando area por id");
			Optional<Persona> persona = this.repository.findById(input.getIdPersona());
			if(!persona.isPresent()) {
				log.info("Persona no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Persona no encontrada", null), HttpStatus.NOT_FOUND);
			}
			log.info("Actualizando nombre de persona");
			persona.get().setNombre(input.getNombre().toUpperCase());
			Persona nPersona = this.repository.save(persona.get());
			
			if(nPersona == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			PersonaWsOutput output = new PersonaWsOutput();
			output.setIdPersona(persona.get().getId());
			output.setNombre(persona.get().getNombre().toUpperCase());
			output.setIdentificacion(persona.get().getIdentificacion());
			output.setEstado(persona.get().getEstado()==1?true:false);
			output.setArea(new AreaWsOutput());
			output.getArea().setIdArea(persona.get().getArea().getId());
			output.getArea().setNombre(persona.get().getArea().getNombre().toUpperCase());
			output.getArea().setEstado(persona.get().getEstado()==1?true:false);
			output.getArea().setCiudad(new CiudadWsOutput());
			output.getArea().getCiudad().setId(persona.get().getArea().getCiudad().getId());
			output.getArea().getCiudad().setNombre(persona.get().getArea().getCiudad().getNombre());
			output.getArea().getCiudad().setEstado(persona.get().getArea().getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Actualizacion Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(DataIntegrityViolationException d) {
			log.info("Error, Clave Duplicada");
			return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida, entrada \""+input.getNombre()+"\" duplicada", null), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
			
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> setPersonaIdentificacion(PersonaWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getIdPersona() < 0 || input.getIdentificacion() == null || input.getIdentificacion().isEmpty()) {
				log.info("Id persona o identificacion persona no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos persona no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando area por id");
			Optional<Persona> persona = this.repository.findById(input.getIdPersona());
			if(!persona.isPresent()) {
				log.info("Persona no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Persona no encontrada", null), HttpStatus.NOT_FOUND);
			}
			log.info("Actualizando identificacion de persona");
			persona.get().setIdentificacion(input.getIdentificacion());
			Persona nPersona = this.repository.save(persona.get());
			
			if(nPersona == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			PersonaWsOutput output = new PersonaWsOutput();
			output.setIdPersona(persona.get().getId());
			output.setNombre(persona.get().getNombre().toUpperCase());
			output.setIdentificacion(persona.get().getIdentificacion());
			output.setEstado(persona.get().getEstado()==1?true:false);
			output.setArea(new AreaWsOutput());
			output.getArea().setIdArea(persona.get().getArea().getId());
			output.getArea().setNombre(persona.get().getArea().getNombre().toUpperCase());
			output.getArea().setEstado(persona.get().getEstado()==1?true:false);
			output.getArea().setCiudad(new CiudadWsOutput());
			output.getArea().getCiudad().setId(persona.get().getArea().getCiudad().getId());
			output.getArea().getCiudad().setNombre(persona.get().getArea().getCiudad().getNombre());
			output.getArea().getCiudad().setEstado(persona.get().getArea().getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Actualizacion Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(DataIntegrityViolationException d) {
			log.info("Error, Clave Duplicada");
			return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida, entrada \""+input.getNombre()+"\" duplicada", null), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> setPersonaEstado(PersonaWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			log.info("Definiendo criterio de busqueda de persona");
			int flat = 0;
			if(input.getIdPersona() >= 0) {
				log.info("Busqueda por id activada");
				flat = 1;
			}
			if(input.getNombre() != null && !input.getNombre().isEmpty()) {
				log.info("Busqueda por nombre activada");
				flat = 2; 
			}
			if(input.getIdentificacion() != null && !input.getIdentificacion().isEmpty()) {
				log.info("Busqueda por identificacion activada");
				flat = 3; 
			}
			
			if(flat == 0) {
				log.info("No se definio ningun criterio de busqueda de persona");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos persona no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			Optional<Persona> persona;
			if(flat == 1) {
				log.info("Realizando busqueda de persona por ID");
				persona = this.repository.findById(input.getIdPersona());
			}
			else {
				if(flat == 2) {
					log.info("Realizando busqueda de persona por Nombre");
					persona = this.repository.findByNombre(input.getNombre().toUpperCase());
				}
				else {
					log.info("Realizando busqueda de persona por Identificacion");
					persona = this.repository.findByIdentificacion(input.getIdentificacion());
				}
			}
			
			if(!persona.isPresent()) {
				log.info("Persona no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Persona no encontrada", null), HttpStatus.NOT_FOUND);
			}
			log.info("Actualizando estado de la persona");
			persona.get().setEstado(input.isEstado()?1:0);
			Persona nPersona = this.repository.save(persona.get());
			
			if(nPersona == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			PersonaWsOutput output = new PersonaWsOutput();
			output.setIdPersona(persona.get().getId());
			output.setNombre(persona.get().getNombre().toUpperCase());
			output.setIdentificacion(persona.get().getIdentificacion());
			output.setEstado(persona.get().getEstado()==1?true:false);
			output.setArea(new AreaWsOutput());
			output.getArea().setIdArea(persona.get().getArea().getId());
			output.getArea().setNombre(persona.get().getArea().getNombre().toUpperCase());
			output.getArea().setEstado(persona.get().getEstado()==1?true:false);
			output.getArea().setCiudad(new CiudadWsOutput());
			output.getArea().getCiudad().setId(persona.get().getArea().getCiudad().getId());
			output.getArea().getCiudad().setNombre(persona.get().getArea().getCiudad().getNombre());
			output.getArea().getCiudad().setEstado(persona.get().getArea().getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Actualizacion Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(DataIntegrityViolationException d) {
			log.info("Error, Clave Duplicada");
			return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida, entrada duplicada", null), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> setPersonaArea(PersonaWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getArea() < 0) {
				log.info("Id Area no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Id de area no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			log.info("Definiendo criterio de busqueda de persona");
			int flat = 0;
			if(input.getIdPersona() >= 0) {
				log.info("Busqueda por id activada");
				flat = 1;
			}
			if(input.getNombre() != null && !input.getNombre().isEmpty()) {
				log.info("Busqueda por nombre activada");
				flat = 2; 
			}
			if(input.getIdentificacion() != null && !input.getIdentificacion().isEmpty()) {
				log.info("Busqueda por identificacion activada");
				flat = 3; 
			}
			
			if(flat == 0) {
				log.info("No se definio ningun criterio de busqueda de persona");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos persona no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			Optional<Persona> persona;
			if(flat == 1) {
				log.info("Realizando busqueda de persona por ID");
				persona = this.repository.findById(input.getIdPersona());
			}
			else {
				if(flat == 2) {
					log.info("Realizando busqueda de persona por Nombre");
					persona = this.repository.findByNombre(input.getNombre().toUpperCase());
				}
				else {
					log.info("Realizando busqueda de persona por Identificacion");
					persona = this.repository.findByIdentificacion(input.getIdentificacion());
				}
			}
			
			if(!persona.isPresent()) {
				log.info("Persona no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Persona no encontrada", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Buscando area por Id");
			Optional<Area> area = this.areaRepository.findById(input.getArea());
			if(!area.isPresent()) {
				log.info("Area no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Area no encontrada", null), HttpStatus.NOT_FOUND);
			}
			
			if(area.get().getEstado() == 0) {
				log.info("Area inactiva");
				return new ResponseEntity<>(new RespuestaGeneralWS("Area inactiva", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Actualizando area de la persona");
			persona.get().setArea(area.get());
			Persona nPersona = this.repository.save(persona.get());
			
			if(nPersona == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			PersonaWsOutput output = new PersonaWsOutput();
			output.setIdPersona(persona.get().getId());
			output.setNombre(persona.get().getNombre().toUpperCase());
			output.setIdentificacion(persona.get().getIdentificacion());
			output.setEstado(persona.get().getEstado()==1?true:false);
			output.setArea(new AreaWsOutput());
			output.getArea().setIdArea(persona.get().getArea().getId());
			output.getArea().setNombre(persona.get().getArea().getNombre().toUpperCase());
			output.getArea().setEstado(persona.get().getEstado()==1?true:false);
			output.getArea().setCiudad(new CiudadWsOutput());
			output.getArea().getCiudad().setId(persona.get().getArea().getCiudad().getId());
			output.getArea().getCiudad().setNombre(persona.get().getArea().getCiudad().getNombre());
			output.getArea().getCiudad().setEstado(persona.get().getArea().getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Actualizacion Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;		
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> addPersona(PersonaWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getArea() < 0) {
				log.info("Id Area no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Id de area no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getNombre() == null || input.getNombre().isEmpty()) {
				log.info("Nombre persona no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Nombre persona no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getIdentificacion() == null || input.getIdentificacion().isEmpty()) {
				log.info("Identificacion persona no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Identificacion persona no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			log.info("Buscando area por Id");
			Optional<Area> area = this.areaRepository.findById(input.getArea());
			if(!area.isPresent()) {
				log.info("Area no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Area no encontrada", null), HttpStatus.NOT_FOUND);
			}
			
			if(area.get().getEstado() == 0) {
				log.info("Area inactiva");
				return new ResponseEntity<>(new RespuestaGeneralWS("Area inactiva", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Cargando datos persona");
			Persona persona = new Persona();
			persona.setNombre(input.getNombre().toUpperCase());
			persona.setIdentificacion(input.getIdentificacion());
			persona.setEstado(input.isEstado()?1:0);
			persona.setArea(area.get());
			
			log.info("Registrando persona");
			Persona nPersona = this.repository.save(persona);
			
			if(nPersona == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			PersonaWsOutput output = new PersonaWsOutput();
			output.setIdPersona(nPersona.getId());
			output.setNombre(nPersona.getNombre().toUpperCase());
			output.setIdentificacion(nPersona.getIdentificacion());
			output.setEstado(nPersona.getEstado()==1?true:false);
			output.setArea(new AreaWsOutput());
			output.getArea().setIdArea(nPersona.getArea().getId());
			output.getArea().setNombre(nPersona.getArea().getNombre().toUpperCase());
			output.getArea().setEstado(nPersona.getEstado()==1?true:false);
			output.getArea().setCiudad(new CiudadWsOutput());
			output.getArea().getCiudad().setId(nPersona.getArea().getCiudad().getId());
			output.getArea().getCiudad().setNombre(nPersona.getArea().getCiudad().getNombre());
			output.getArea().getCiudad().setEstado(nPersona.getArea().getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Actualizacion Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(DataIntegrityViolationException d) {
			log.info("Error, Clave Duplicada");
			return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida, entrada duplicada", null), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

}
