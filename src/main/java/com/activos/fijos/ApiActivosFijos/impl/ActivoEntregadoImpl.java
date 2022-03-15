package com.activos.fijos.ApiActivosFijos.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.activos.fijos.ApiActivosFijos.entity.ActivoEntregadoArea;
import com.activos.fijos.ApiActivosFijos.entity.ActivoEntregadoPersona;
import com.activos.fijos.ApiActivosFijos.entity.ActivoFijo;
import com.activos.fijos.ApiActivosFijos.entity.Area;
import com.activos.fijos.ApiActivosFijos.entity.Persona;
import com.activos.fijos.ApiActivosFijos.entity.TipoEntrega;
import com.activos.fijos.ApiActivosFijos.interfaces.IGestionarActivoEntregado;
import com.activos.fijos.ApiActivosFijos.repository.ActivoEntregadoAreaRepository;
import com.activos.fijos.ApiActivosFijos.repository.ActivoEntregadoPersonaRepository;
import com.activos.fijos.ApiActivosFijos.repository.ActivoFijoRepository;
import com.activos.fijos.ApiActivosFijos.repository.AreaRepository;
import com.activos.fijos.ApiActivosFijos.repository.PersonaRepository;
import com.activos.fijos.ApiActivosFijos.repository.TipoEntregaRepository;
import com.activos.fijos.ApiActivosFijos.ws.input.ActivoEntregadoWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.ActivoEntregadoWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.ActivoFijoWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.PersonaWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

@Service
public class ActivoEntregadoImpl implements IGestionarActivoEntregado {
	
	private static final Logger log = LoggerFactory.getLogger(ActivoEntregadoImpl.class);
	
	@Autowired
	private ActivoEntregadoAreaRepository AEARepository;
	
	@Autowired
	private ActivoEntregadoPersonaRepository AEPRepository;
	
	@Autowired
	private ActivoFijoRepository AFRepository;
	
	@Autowired
	private TipoEntregaRepository TERepository;
	
	@Autowired
	private PersonaRepository PRepository;
	
	@Autowired
	private AreaRepository ARepository;

	@Override
	public ResponseEntity<RespuestaGeneralWS> getActivoEntregado(int id) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity(null, HttpStatus.OK);
		try {
			if(id < 0) {
				log.info("Activo fijo no permitido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Activo fijo no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			Optional<ActivoFijo> activoFijo = this.AFRepository.findById(id);
			if(!activoFijo.isPresent()) {
				log.info("Activo Fijo no encontrado");
				return new ResponseEntity<>(new RespuestaGeneralWS("Activo Fijo no encontrado", null), HttpStatus.NOT_FOUND);
			}
			if(activoFijo.get().getEntregado() == 0) {
				log.info("Activo Fijo no entregado");
				return new ResponseEntity<>(new RespuestaGeneralWS("Activo Fijo no entregado", null), HttpStatus.NOT_FOUND);
			}
			
			Optional<ActivoEntregadoArea> entregadoArea = this.AEARepository.finddByActivoFijo(id);
			if(!entregadoArea.isPresent()) {
				Optional<ActivoEntregadoPersona> entregadoPersona = this.AEPRepository.finddByActivoFijo(id);
				if(entregadoPersona.isPresent()) {
					log.info("Preparando Respuesta");
					ActivoEntregadoWsOutput output = new ActivoEntregadoWsOutput();
					output.setId(entregadoPersona.get().getId());
					output.setTipoEntrega(entregadoPersona.get().getTipoEntrega().getDescripcion());
					output.setNombreEntregado(entregadoPersona.get().getIdEntregado().getIdentificacion() + "-" + entregadoPersona.get().getIdEntregado().getNombre());
					output.setFechaEntrega(new java.util.Date(entregadoPersona.get().getFechaEntrega().getTime()));
					output.setFechaRetiro(entregadoPersona.get().getFechaRetiro()==null?null:new java.util.Date(entregadoPersona.get().getFechaRetiro().getTime()));
					
					output.setActivoFijo(new ActivoFijoWsOutput());
					output.getActivoFijo().setId(entregadoPersona.get().getActivoFijo().getId());
					output.getActivoFijo().setNombre(entregadoPersona.get().getActivoFijo().getNombre());
					output.getActivoFijo().setTipoActivo(entregadoPersona.get().getActivoFijo().getTipoActivo().getDescripcion());
					output.getActivoFijo().setSerial(entregadoPersona.get().getActivoFijo().getSerial());
					output.getActivoFijo().setNumeroInternoInventario(entregadoPersona.get().getActivoFijo().getNumeroInternoInventario());
					output.getActivoFijo().setPeso(entregadoPersona.get().getActivoFijo().getPeso());
					output.getActivoFijo().setAlto(entregadoPersona.get().getActivoFijo().getAlto());
					output.getActivoFijo().setAncho(entregadoPersona.get().getActivoFijo().getAncho());
					output.getActivoFijo().setLargo(entregadoPersona.get().getActivoFijo().getLargo());
					output.getActivoFijo().setValorcompra(entregadoPersona.get().getActivoFijo().getValorCompra());
					output.getActivoFijo().setFechaCompra(new java.util.Date(entregadoPersona.get().getActivoFijo().getFechaCompra().getTime()));
					output.getActivoFijo().setEntregado(entregadoPersona.get().getActivoFijo().getEntregado()==1?true:false);
					output.getActivoFijo().setEstado(entregadoPersona.get().getActivoFijo().getEstado()==1?true:false);
					
					RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
					respuestaDto.setDescripcion("Consulta Exitosa");
					respuestaDto.setObj(output);
					
					respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
				}
			}
			else {
				log.info("Preparando Respuesta");
				ActivoEntregadoWsOutput output = new ActivoEntregadoWsOutput();
				output.setId(entregadoArea.get().getId());
				output.setTipoEntrega(entregadoArea.get().getTipoEntrega().getDescripcion());
				output.setNombreEntregado(entregadoArea.get().getIdEntregado().getId() + "-" + entregadoArea.get().getIdEntregado().getNombre());
				output.setFechaEntrega(new java.util.Date(entregadoArea.get().getFechaEntrega().getTime()));
				output.setFechaRetiro(new java.util.Date(entregadoArea.get().getFechaRetiro().getTime()));
				
				output.setActivoFijo(new ActivoFijoWsOutput());
				output.getActivoFijo().setId(entregadoArea.get().getActivoFijo().getId());
				output.getActivoFijo().setNombre(entregadoArea.get().getActivoFijo().getNombre());
				output.getActivoFijo().setTipoActivo(entregadoArea.get().getActivoFijo().getTipoActivo().getDescripcion());
				output.getActivoFijo().setSerial(entregadoArea.get().getActivoFijo().getSerial());
				output.getActivoFijo().setNumeroInternoInventario(entregadoArea.get().getActivoFijo().getNumeroInternoInventario());
				output.getActivoFijo().setPeso(entregadoArea.get().getActivoFijo().getPeso());
				output.getActivoFijo().setAlto(entregadoArea.get().getActivoFijo().getAlto());
				output.getActivoFijo().setAncho(entregadoArea.get().getActivoFijo().getAncho());
				output.getActivoFijo().setLargo(entregadoArea.get().getActivoFijo().getLargo());
				output.getActivoFijo().setValorcompra(entregadoArea.get().getActivoFijo().getValorCompra());
				output.getActivoFijo().setFechaCompra(new java.util.Date(entregadoArea.get().getActivoFijo().getFechaCompra().getTime()));
				output.getActivoFijo().setEntregado(entregadoArea.get().getActivoFijo().getEntregado()==1?true:false);
				output.getActivoFijo().setEstado(entregadoArea.get().getActivoFijo().getEstado()==1?true:false);
				
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Consulta Exitosa");
				respuestaDto.setObj(output);
				
				respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
			}
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> setActivoEntregado(ActivoEntregadoWsInput input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> addActivoEntregado(ActivoEntregadoWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity(null, HttpStatus.OK);
		try {
			if(input.getActivoFijo() < 0) {
				log.info("Activo fijo no permitido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Activo fijo no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			if(input.getTipoEntrega() < 0) {
				log.info("Tipo Entrega no permitido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Tipo Entrega no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			if(input.getIdEntregado() < 0) {
				log.info("Entregado no permitido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Entregado no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			if(input.getFechaEntrega() == null) {
				log.info("Fecha Entrega no permitido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Fecha Entrega no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			Optional<ActivoFijo> activoFijo = this.AFRepository.findById(input.getActivoFijo());
			if(!activoFijo.isPresent()) {
				log.info("Activo Fijo no encontrado");
				return new ResponseEntity<>(new RespuestaGeneralWS("Activo Fijo no encontrado", null), HttpStatus.NOT_FOUND);
			}
			
			if(activoFijo.get().getEstado() == 0) {
				log.info("Activo Fijo inactivo");
				return new ResponseEntity<>(new RespuestaGeneralWS("Activo Fijo inactivo", null), HttpStatus.NOT_FOUND);
			}
			
			if(activoFijo.get().getEntregado() == 1) {
				log.info("Activo Fijo ya entregado");
				return new ResponseEntity<>(new RespuestaGeneralWS("Activo Fijo ya entregado", null), HttpStatus.NOT_FOUND);
			}
			
			Optional<TipoEntrega> tipoEntrega = this.TERepository.findById(input.getTipoEntrega());
			if(!tipoEntrega.isPresent()) {
				log.info("Tipo Entrega no encontrado");
				return new ResponseEntity<>(new RespuestaGeneralWS("Tipo Entrega no encontrado", null), HttpStatus.NOT_FOUND);
			}
			
			if(tipoEntrega.get().getDescripcion().equalsIgnoreCase("PERSONA")) {
				Optional<Persona> persona = this.PRepository.findById(input.getIdEntregado());
				if(!persona.isPresent()) {
					log.info("Persona no encontrado");
					return new ResponseEntity<>(new RespuestaGeneralWS("Persona no encontrado", null), HttpStatus.NOT_FOUND);
				}
				if(persona.get().getEstado() == 0) {
					log.info("Persona inactiva");
					return new ResponseEntity<>(new RespuestaGeneralWS("Persona inactiva", null), HttpStatus.NOT_FOUND);
				}
				
				log.info("Cargar Datos para Persona");
				ActivoEntregadoPersona entregado = new ActivoEntregadoPersona();
				entregado.setTipoEntrega(tipoEntrega.get());
				entregado.setIdEntregado(persona.get());
				entregado.setFechaEntrega(new java.sql.Date(input.getFechaEntrega().getTime()));
				entregado.setFechaRetiro(null);
				entregado.setActivoFijo(activoFijo.get());
				entregado.getActivoFijo().setEntregado(1);
				
				log.info("Registrando Entrega a Persona");
				ActivoEntregadoPersona nEntregado = this.AEPRepository.save(entregado);
				
				if(nEntregado == null) {
					log.info("Error en la actualizacion");
					return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
				}
				
				log.info("Preparando Respuesta");
				ActivoEntregadoWsOutput output = new ActivoEntregadoWsOutput();
				output.setId(nEntregado.getId());
				output.setTipoEntrega(nEntregado.getTipoEntrega().getDescripcion());
				output.setNombreEntregado(nEntregado.getIdEntregado().getIdentificacion() + "-" + nEntregado.getIdEntregado().getNombre());
				output.setFechaEntrega(new java.util.Date(nEntregado.getFechaEntrega().getTime()));
				output.setFechaRetiro(nEntregado.getFechaRetiro()==null?null:new java.util.Date(nEntregado.getFechaRetiro().getTime()));
				
				output.setActivoFijo(new ActivoFijoWsOutput());
				output.getActivoFijo().setId(nEntregado.getActivoFijo().getId());
				output.getActivoFijo().setNombre(nEntregado.getActivoFijo().getNombre());
				output.getActivoFijo().setTipoActivo(nEntregado.getActivoFijo().getTipoActivo().getDescripcion());
				output.getActivoFijo().setSerial(nEntregado.getActivoFijo().getSerial());
				output.getActivoFijo().setNumeroInternoInventario(nEntregado.getActivoFijo().getNumeroInternoInventario());
				output.getActivoFijo().setPeso(nEntregado.getActivoFijo().getPeso());
				output.getActivoFijo().setAlto(nEntregado.getActivoFijo().getAlto());
				output.getActivoFijo().setAncho(nEntregado.getActivoFijo().getAncho());
				output.getActivoFijo().setLargo(nEntregado.getActivoFijo().getLargo());
				output.getActivoFijo().setValorcompra(nEntregado.getActivoFijo().getValorCompra());
				output.getActivoFijo().setFechaCompra(new java.util.Date(nEntregado.getActivoFijo().getFechaCompra().getTime()));
				output.getActivoFijo().setEntregado(nEntregado.getActivoFijo().getEntregado()==1?true:false);
				output.getActivoFijo().setEstado(nEntregado.getActivoFijo().getEstado()==1?true:false);
				
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Actualización Exitosa");
				respuestaDto.setObj(output);
				
				respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
			}
			else {
				if(tipoEntrega.get().getDescripcion().equalsIgnoreCase("AREA")) {
					Optional<Area> area = this.ARepository.findById(input.getIdEntregado());
					if(!area.isPresent()) {
						log.info("Area no encontrado");
						return new ResponseEntity<>(new RespuestaGeneralWS("Area no encontrado", null), HttpStatus.NOT_FOUND);
					}
					if(area.get().getEstado() == 0) {
						log.info("Area inactiva");
						return new ResponseEntity<>(new RespuestaGeneralWS("Area inactiva", null), HttpStatus.NOT_FOUND);
					}
					
					log.info("Cargar Datos para Area");
					ActivoEntregadoArea entregado = new ActivoEntregadoArea();
					entregado.setTipoEntrega(tipoEntrega.get());
					entregado.setIdEntregado(area.get());
					entregado.setFechaEntrega(new java.sql.Date(input.getFechaEntrega().getTime()));
					entregado.setFechaRetiro(null);
					entregado.setActivoFijo(activoFijo.get());
					entregado.getActivoFijo().setEntregado(1);
					
					log.info("Registrando Entrega a Area");
					ActivoEntregadoArea nEntregado = this.AEARepository.save(entregado);
					
					if(nEntregado == null) {
						log.info("Error en la actualizacion");
						return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
					}
					
					log.info("Preparando Respuesta");
					ActivoEntregadoWsOutput output = new ActivoEntregadoWsOutput();
					output.setId(nEntregado.getId());
					output.setTipoEntrega(nEntregado.getTipoEntrega().getDescripcion());
					output.setNombreEntregado(nEntregado.getIdEntregado().getId() + "-" + nEntregado.getIdEntregado().getNombre());
					output.setFechaEntrega(new java.util.Date(nEntregado.getFechaEntrega().getTime()));
					output.setFechaRetiro(nEntregado.getFechaRetiro()==null?null:new java.util.Date(nEntregado.getFechaRetiro().getTime()));
					
					output.setActivoFijo(new ActivoFijoWsOutput());
					output.getActivoFijo().setId(nEntregado.getActivoFijo().getId());
					output.getActivoFijo().setNombre(nEntregado.getActivoFijo().getNombre());
					output.getActivoFijo().setTipoActivo(nEntregado.getActivoFijo().getTipoActivo().getDescripcion());
					output.getActivoFijo().setSerial(nEntregado.getActivoFijo().getSerial());
					output.getActivoFijo().setNumeroInternoInventario(nEntregado.getActivoFijo().getNumeroInternoInventario());
					output.getActivoFijo().setPeso(nEntregado.getActivoFijo().getPeso());
					output.getActivoFijo().setAlto(nEntregado.getActivoFijo().getAlto());
					output.getActivoFijo().setAncho(nEntregado.getActivoFijo().getAncho());
					output.getActivoFijo().setLargo(nEntregado.getActivoFijo().getLargo());
					output.getActivoFijo().setValorcompra(nEntregado.getActivoFijo().getValorCompra());
					output.getActivoFijo().setFechaCompra(new java.util.Date(nEntregado.getActivoFijo().getFechaCompra().getTime()));
					output.getActivoFijo().setEntregado(nEntregado.getActivoFijo().getEntregado()==1?true:false);
					output.getActivoFijo().setEstado(nEntregado.getActivoFijo().getEstado()==1?true:false);
					
					RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
					respuestaDto.setDescripcion("Actualización Exitosa");
					respuestaDto.setObj(output);
					
					respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);
				}
			}			
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
