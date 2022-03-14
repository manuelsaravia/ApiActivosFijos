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

import com.activos.fijos.ApiActivosFijos.entity.Ciudad;
import com.activos.fijos.ApiActivosFijos.interfaces.IGestionarCiudad;
import com.activos.fijos.ApiActivosFijos.repository.CiudadRepository;
import com.activos.fijos.ApiActivosFijos.ws.input.CiudadWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.CiudadWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

@Service
public class CiudadImpl implements IGestionarCiudad{
	
	private static final Logger log = LoggerFactory.getLogger(CiudadImpl.class);
	
	@Autowired
	private CiudadRepository repository;

	@Override
	public ResponseEntity<RespuestaGeneralWS> getCiudadId(int idCiudad) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(idCiudad < 0) {
				log.info("id de ciudad inferior a 0");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Id Ciudad no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando ciudad por id");
			Optional<Ciudad> ciudad = this.repository.findById(idCiudad);
			if(!ciudad.isPresent()) {
				log.info("Ciudad no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			CiudadWsOutput output = new CiudadWsOutput();
			output.setId(ciudad.get().getId());
			output.setNombre(ciudad.get().getNombre().toUpperCase());
			output.setEstado(ciudad.get().getEstado()==1?true:false);
			
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
	public ResponseEntity<RespuestaGeneralWS> getCiudadNombre(String nombre) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(nombre == null || nombre.isEmpty()) {
				log.info("Nombre de ciudad no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Nombre Ciudad no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando ciudad por nombre");
			Optional<Ciudad> ciudad = this.repository.findByNombre(nombre.toUpperCase());
			if(!ciudad.isPresent()) {
				log.info("Ciudad no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			CiudadWsOutput output = new CiudadWsOutput();
			output.setId(ciudad.get().getId());
			output.setNombre(ciudad.get().getNombre().toUpperCase());
			output.setEstado(ciudad.get().getEstado()==1?true:false);
			
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
	public ResponseEntity<Object> getCiudades() {
		ResponseEntity<Object> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			log.info("Listando ciudades");
			List<Ciudad> ciudades = this.repository.findAll();
			if(ciudades == null || ciudades.isEmpty()) {
				log.info("Listado de ciudades vacio");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			List<CiudadWsOutput> listadoCiudades = new ArrayList<>();
			log.info("Recorriendo ciudades");
			for(Ciudad ciudad: ciudades) {
				CiudadWsOutput output = new CiudadWsOutput();
				output.setId(ciudad.getId());
				output.setNombre(ciudad.getNombre().toUpperCase());
				output.setEstado(ciudad.getEstado()==1?true:false);
				
				listadoCiudades.add(output);
			}
			
			log.info("Preparando respuesta");
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Consulta Exitosa");
			respuestaDto.setObj(listadoCiudades);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> setCiudadNombre(CiudadWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getIdCiudad() < 0 || input.getNombreCiudad() == null || input.getNombreCiudad().isEmpty()) {
				log.info("Id Ciudad o Nombre Ciudad no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos Ciudad no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando ciudad por id");
			Optional<Ciudad> ciudad = this.repository.findById(input.getIdCiudad());
			if(!ciudad.isPresent()) {
				log.info("Ciudad no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Ciudad no encontrada", null), HttpStatus.NOT_FOUND);
			}
			log.info("Actualizando nombre de ciudad");
			ciudad.get().setNombre(input.getNombreCiudad().toUpperCase());
			Ciudad nCiudad = this.repository.save(ciudad.get());
			
			if(nCiudad == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			CiudadWsOutput output = new CiudadWsOutput();
			output.setId(nCiudad.getId());
			output.setNombre(nCiudad.getNombre().toUpperCase());
			output.setEstado(nCiudad.getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Actualización Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(DataIntegrityViolationException d) {
			log.info("Error, Clave Duplicada");
			return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida, entrada \""+input.getNombreCiudad()+"\" duplicada", null), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> setCiudadEstado(CiudadWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			log.info("Definiendo criterio de busqueda de ciudad");
			int flat = 0;
			if(input.getIdCiudad() >= 0) {
				log.info("Busqueda por id activada");
				flat = 1;
			}
			if(input.getNombreCiudad() != null && !input.getNombreCiudad().isEmpty()) {
				log.info("Busqueda por nombre activada");
				flat = 2; 
			}
			
			if(flat == 0) {
				log.info("No se definio ningun criterio de busqueda de ciudad");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos Ciudad no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			Optional<Ciudad> ciudad;
			if(flat == 1) {
				log.info("Realizando busqueda de ciudad por ID");
				ciudad = this.repository.findById(input.getIdCiudad());
			}
			else {
				log.info("Realizando busqueda de ciudad por Nombre");
				ciudad = this.repository.findByNombre(input.getNombreCiudad().toUpperCase());	
			}
			
			if(!ciudad.isPresent()) {
				log.info("Ciudad no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Ciudad no encontrada", null), HttpStatus.NOT_FOUND);
			}
			log.info("Actualizando estado de la ciudad");
			ciudad.get().setEstado(input.isEstado()?1:0);
			Ciudad nCiudad = this.repository.save(ciudad.get());
			
			if(nCiudad == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando la respuesta");
			CiudadWsOutput output = new CiudadWsOutput();
			output.setId(nCiudad.getId());
			output.setNombre(nCiudad.getNombre().toUpperCase());
			output.setEstado(nCiudad.getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Actualización Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(DataIntegrityViolationException d) {
			log.info("Error, clave duplicada");
			return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida, entrada duplicada", null), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> addCiudad(CiudadWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getNombreCiudad() == null || input.getNombreCiudad().isEmpty()) {
				log.info("Id Ciudad o Nombre Ciudad no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos Ciudad no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Cargando datos ciudad");
			Ciudad ciudad = new Ciudad();
			ciudad.setNombre(input.getNombreCiudad());
			ciudad.setEstado(input.isEstado()?1:0);
			
			log.info("Registrando ciudad");
			Ciudad nCiudad = this.repository.save(ciudad);
			
			if(nCiudad == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Insercion no permitida", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			CiudadWsOutput output = new CiudadWsOutput();
			output.setId(nCiudad.getId());
			output.setNombre(nCiudad.getNombre().toUpperCase());
			output.setEstado(nCiudad.getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Insercion Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(DataIntegrityViolationException d) {
			log.info("Error, Clave Duplicada");
			return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida, entrada \""+input.getNombreCiudad()+"\" duplicada", null), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

}
