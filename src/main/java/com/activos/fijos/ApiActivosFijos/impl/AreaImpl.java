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
import com.activos.fijos.ApiActivosFijos.interfaces.IGestionarArea;
import com.activos.fijos.ApiActivosFijos.repository.AreaRepository;
import com.activos.fijos.ApiActivosFijos.repository.CiudadRepository;
import com.activos.fijos.ApiActivosFijos.ws.input.AreaWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.AreaWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.CiudadWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

@Service
public class AreaImpl implements IGestionarArea {
	
	private static final Logger log = LoggerFactory.getLogger(AreaImpl.class);
	
	@Autowired
	private AreaRepository repository;
	
	@Autowired
	private CiudadRepository ciudadRepository;

	@Override
	public ResponseEntity<RespuestaGeneralWS> getAreaId(int idArea) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(idArea < 0) {
				log.info("id de Area inferior a 0");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Id Area no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando area por id");
			Optional<Area> area = this.repository.findById(idArea);
			if(!area.isPresent()) {
				log.info("Area no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			AreaWsOutput output = new AreaWsOutput();
			output.setIdArea(area.get().getId());
			output.setNombre(area.get().getNombre().toUpperCase());
			output.setEstado(area.get().getEstado()==1?true:false);
			output.setCiudad(new CiudadWsOutput());
			output.getCiudad().setId(area.get().getCiudad().getId());
			output.getCiudad().setNombre(area.get().getCiudad().getNombre());
			output.getCiudad().setEstado(area.get().getCiudad().getEstado()==1?true:false);
			
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
	public ResponseEntity<RespuestaGeneralWS> getAreaNombre(String nombre) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(nombre == null || nombre.isEmpty()) {
				log.info("Nombre de area no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Nombre area no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando area por nombre");
			Optional<Area> area = this.repository.findByNombre(nombre.toUpperCase());
			if(!area.isPresent()) {
				log.info("Area no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			AreaWsOutput output = new AreaWsOutput();
			output.setIdArea(area.get().getId());
			output.setNombre(area.get().getNombre().toUpperCase());
			output.setEstado(area.get().getEstado()==1?true:false);
			output.setCiudad(new CiudadWsOutput());
			output.getCiudad().setId(area.get().getCiudad().getId());
			output.getCiudad().setNombre(area.get().getCiudad().getNombre());
			output.getCiudad().setEstado(area.get().getCiudad().getEstado()==1?true:false);
			
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
	public ResponseEntity<Object> getAreas() {
		ResponseEntity<Object> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			log.info("Listando areas");
			List<Area> areas = this.repository.findAll();
			if(areas == null || areas.isEmpty()) {
				log.info("Listado de areas vacio");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			List<AreaWsOutput> listadoAreas = new ArrayList<>();
			log.info("Recorriendo areas");
			for(Area area: areas) {
				AreaWsOutput output = new AreaWsOutput();
				output.setIdArea(area.getId());
				output.setNombre(area.getNombre().toUpperCase());
				output.setEstado(area.getEstado()==1?true:false);
				output.setCiudad(new CiudadWsOutput());
				output.getCiudad().setId(area.getCiudad().getId());
				output.getCiudad().setNombre(area.getCiudad().getNombre());
				output.getCiudad().setEstado(area.getCiudad().getEstado()==1?true:false);
				
				listadoAreas.add(output);
			}
			
			log.info("Preparando respuesta");
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Consulta Exitosa");
			respuestaDto.setObj(listadoAreas);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> setAreaNombre(AreaWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getIdArea() < 0 || input.getNombre() == null || input.getNombre().isEmpty()) {
				log.info("Id area o Nombre area no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos area no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			log.info("Buscando area por id");
			Optional<Area> area = this.repository.findById(input.getIdArea());
			if(!area.isPresent()) {
				log.info("Area no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Area no encontrada", null), HttpStatus.NOT_FOUND);
			}
			log.info("Actualizando nombre de area");
			area.get().setNombre(input.getNombre().toUpperCase());
			Area nArea = this.repository.save(area.get());
			
			if(nArea == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando respuesta");
			AreaWsOutput output = new AreaWsOutput();
			output.setIdArea(nArea.getId());
			output.setNombre(nArea.getNombre().toUpperCase());
			output.setEstado(nArea.getEstado()==1?true:false);
			output.setCiudad(new CiudadWsOutput());
			output.getCiudad().setId(nArea.getCiudad().getId());
			output.getCiudad().setNombre(nArea.getCiudad().getNombre());
			output.getCiudad().setEstado(nArea.getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Actualización Exitosa");
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
	public ResponseEntity<RespuestaGeneralWS> setAreaEstado(AreaWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			log.info("Definiendo criterio de busqueda de area");
			int flat = 0;
			if(input.getIdCiudad() >= 0) {
				log.info("Busqueda por id activada");
				flat = 1;
			}
			if(input.getNombre() != null && !input.getNombre().isEmpty()) {
				log.info("Busqueda por nombre activada");
				flat = 2; 
			}
			
			if(flat == 0) {
				log.info("No se definio ningun criterio de busqueda de area");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos area no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			Optional<Area> area;
			if(flat == 1) {
				log.info("Realizando busqueda de area por ID");
				area = this.repository.findById(input.getIdArea());
			}
			else {
				log.info("Realizando busqueda de area por Nombre");
				area = this.repository.findByNombre(input.getNombre().toUpperCase());	
			}
			
			if(!area.isPresent()) {
				log.info("Area no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Area no encontrada", null), HttpStatus.NOT_FOUND);
			}
			log.info("Actualizando estado de la area");
			area.get().setEstado(input.isEstado()?1:0);
			Area nArea = this.repository.save(area.get());
			
			if(nArea == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			log.info("Preparando la respuesta");
			AreaWsOutput output = new AreaWsOutput();
			output.setIdArea(nArea.getId());
			output.setNombre(nArea.getNombre().toUpperCase());
			output.setEstado(nArea.getEstado()==1?true:false);
			output.setCiudad(new CiudadWsOutput());
			output.getCiudad().setId(nArea.getCiudad().getId());
			output.getCiudad().setNombre(nArea.getCiudad().getNombre());
			output.getCiudad().setEstado(nArea.getCiudad().getEstado()==1?true:false);
			
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
	public ResponseEntity<RespuestaGeneralWS> setAreaCiudad(AreaWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getIdCiudad() < 0) {
				log.info("Id ciudad no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Id de ciudad no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			log.info("Definiendo criterio de busqueda de area");
			int flat = 0;
			if(input.getIdCiudad() >= 0) {
				log.info("Busqueda por id activada");
				flat = 1;
			}
			if(input.getNombre() != null && !input.getNombre().isEmpty()) {
				log.info("Busqueda por nombre activada");
				flat = 2; 
			}
			
			if(flat == 0) {
				log.info("No se definio ningun criterio de busqueda de area");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos area no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			Optional<Area> area;
			if(flat == 1) {
				log.info("Realizando busqueda de area por ID");
				area = this.repository.findById(input.getIdArea());
			}
			else {
				log.info("Realizando busqueda de area por Nombre");
				area = this.repository.findByNombre(input.getNombre().toUpperCase());	
			}
			
			if(!area.isPresent()) {
				log.info("Area no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Area no encontrada", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Buscando ciudad por Id");
			Optional<Ciudad> ciudad = this.ciudadRepository.findById(input.getIdCiudad());
			if(!ciudad.isPresent()) {
				log.info("Ciudad no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Ciudad no encontrada", null), HttpStatus.NOT_FOUND);
			}
			
			if(ciudad.get().getEstado() == 0) {
				log.info("Ciudad inactiva");
				return new ResponseEntity<>(new RespuestaGeneralWS("Ciudad inactiva", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Actualizando ciudad del area");
			area.get().setCiudad(ciudad.get());
			Area nArea = this.repository.save(area.get());
			
			if(nArea == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Preparando la respuesta");
			AreaWsOutput output = new AreaWsOutput();
			output.setIdArea(nArea.getId());
			output.setNombre(nArea.getNombre().toUpperCase());
			output.setEstado(nArea.getEstado()==1?true:false);
			output.setCiudad(new CiudadWsOutput());
			output.getCiudad().setId(nArea.getCiudad().getId());
			output.getCiudad().setNombre(nArea.getCiudad().getNombre());
			output.getCiudad().setEstado(nArea.getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Actualización Exitosa");
			respuestaDto.setObj(output);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> addArea(AreaWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getIdCiudad() < 0) {
				log.info("Id ciudad no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Id de ciudad no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getNombre() == null || input.getNombre().isEmpty()) {
				log.info("Nombre area no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos area no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			log.info("Buscando ciudad por Id");
			Optional<Ciudad> ciudad = this.ciudadRepository.findById(input.getIdCiudad());
			if(!ciudad.isPresent()) {
				log.info("Ciudad no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Ciudad no encontrada", null), HttpStatus.NOT_FOUND);
			}
			
			if(ciudad.get().getEstado() == 0) {
				log.info("Ciudad inactiva");
				return new ResponseEntity<>(new RespuestaGeneralWS("Ciudad inactiva", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Cargando datos area");
			Area area = new Area();
			area.setEstado(input.isEstado()?1:0);
			area.setNombre(input.getNombre().toUpperCase());
			area.setCiudad(ciudad.get());
			
			log.info("Registrando Area");
			Area nArea = this.repository.save(area);
			
			if(nArea == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Preparando la respuesta");
			AreaWsOutput output = new AreaWsOutput();
			output.setIdArea(nArea.getId());
			output.setNombre(nArea.getNombre().toUpperCase());
			output.setEstado(nArea.getEstado()==1?true:false);
			output.setCiudad(new CiudadWsOutput());
			output.getCiudad().setId(nArea.getCiudad().getId());
			output.getCiudad().setNombre(nArea.getCiudad().getNombre());
			output.getCiudad().setEstado(nArea.getCiudad().getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Actualización Exitosa");
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

}
