package com.activos.fijos.ApiActivosFijos.impl;

import java.sql.Date;
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

import com.activos.fijos.ApiActivosFijos.entity.ActivoFijo;
import com.activos.fijos.ApiActivosFijos.entity.Area;
import com.activos.fijos.ApiActivosFijos.entity.Persona;
import com.activos.fijos.ApiActivosFijos.entity.TipoActivo;
import com.activos.fijos.ApiActivosFijos.interfaces.IGestionarActivoFijo;
import com.activos.fijos.ApiActivosFijos.repository.ActivoFijoRepository;
import com.activos.fijos.ApiActivosFijos.repository.TipoActivoRepository;
import com.activos.fijos.ApiActivosFijos.ws.input.ActivoFijoWsInput;
import com.activos.fijos.ApiActivosFijos.ws.output.ActivoFijoWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.PersonaWsOutput;
import com.activos.fijos.ApiActivosFijos.ws.output.RespuestaGeneralWS;

@Service
public class ActivoFijoImpl implements IGestionarActivoFijo {
	
	private static final Logger log = LoggerFactory.getLogger(ActivoFijoImpl.class);
	
	@Autowired
	private ActivoFijoRepository repository;
	
	@Autowired
	private TipoActivoRepository tipoActivoRepository;

	@Override
	public ResponseEntity<RespuestaGeneralWS> getActivoFijo(ActivoFijoWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			log.info("Definiendo criterio de busqueda de persona");
			int flat = 0;
			if(input.getId() > 0) {
				log.info("Busqueda por id activada");
				flat = 1;
			}
			else {
				if(input.getNombre() != null && !input.getNombre().isEmpty()) {
					log.info("Busqueda por nombre activada");
					flat = 2;
				}
				else {
					if(input.getSerial() != null && !input.getSerial().isEmpty()) {
						log.info("Busqueda por serial activada");
						flat = 3;
					}
					else {
						if(input.getNumeroInternoInventario() != null && !input.getNumeroInternoInventario().isEmpty()) {
							log.info("Busqueda por numero inventario activada");
							flat = 4;
						}
					}
				}
			}
			
			Optional<ActivoFijo> activoFijo = null;
			
			switch(flat) {
			
			
			case 0:
				log.info("No se definio ningun criterio de busqueda de persona");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Datos de activo no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
				
			case 1:
				log.info("Realizando busqueda de activo fijo por Id");
				activoFijo = this.repository.findById(input.getId());
				break;
				
			case 2:
				log.info("Realizando busqueda de activo fijo por nombre");
				activoFijo = this.repository.findByNombre(input.getNombre());
				break;
				
			case 3:
				log.info("Realizando busqueda de activo fijo por serial");
				activoFijo = this.repository.findBySerial(input.getSerial());
				break;
				
			case 4:
				log.info("Realizando busqueda de activo fijo por numero inventario");
				activoFijo = this.repository.findByNumeroInternoInventario(input.getNumeroInternoInventario());
				break;
			}
			
			if(!activoFijo.isPresent()) {
				log.info("Activo Fijo no encontrado");
				return new ResponseEntity<>(new RespuestaGeneralWS("Activo Fijo no encontrado", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Preparando respuesta");
			ActivoFijoWsOutput output = new ActivoFijoWsOutput();
			output.setId(activoFijo.get().getId());
			output.setNombre(activoFijo.get().getNombre());
			output.setTipoActivo(activoFijo.get().getTipoActivo().getDescripcion());
			output.setSerial(activoFijo.get().getSerial());
			output.setNumeroInternoInventario(activoFijo.get().getNumeroInternoInventario());
			output.setPeso(activoFijo.get().getPeso());
			output.setAlto(activoFijo.get().getAlto());
			output.setAncho(activoFijo.get().getAncho());
			output.setLargo(activoFijo.get().getLargo());
			output.setValorcompra(activoFijo.get().getValorCompra());
			output.setFechaCompra(activoFijo.get().getFechaCompra());
			output.setEntregado(activoFijo.get().getEntregado()==1?true:false);
			output.setEstado(activoFijo.get().getEstado()==1?true:false);
			
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
	public ResponseEntity<Object> getActivoFijos() {
		ResponseEntity<Object> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			log.info("Listando Activos");
			List<ActivoFijo> activosFijos = this.repository.findAll();
			if(activosFijos == null || activosFijos.isEmpty()) {
				log.info("Listado de activos Fijos vacio");
				return new ResponseEntity<>(new RespuestaGeneralWS("Busqueda sin resultados", null), HttpStatus.NOT_FOUND);
			}
			List<ActivoFijoWsOutput> listadoActivo = new ArrayList<>();
			log.info("Recorriendo activos Fijos");
			for(ActivoFijo nActivoFijo: activosFijos) {
				log.info("Preparando respuesta");
				ActivoFijoWsOutput output = new ActivoFijoWsOutput();
				output.setId(nActivoFijo.getId());
				output.setNombre(nActivoFijo.getNombre());
				output.setTipoActivo(nActivoFijo.getTipoActivo().getDescripcion());
				output.setSerial(nActivoFijo.getSerial());
				output.setNumeroInternoInventario(nActivoFijo.getNumeroInternoInventario());
				output.setPeso(nActivoFijo.getPeso());
				output.setAlto(nActivoFijo.getAlto());
				output.setAncho(nActivoFijo.getAncho());
				output.setLargo(nActivoFijo.getLargo());
				output.setValorcompra(nActivoFijo.getValorCompra());
				output.setFechaCompra(new java.util.Date(nActivoFijo.getFechaCompra().getTime()));
				output.setEntregado(nActivoFijo.getEntregado()==1?true:false);
				output.setEstado(nActivoFijo.getEstado()==1?true:false);
				
				listadoActivo.add(output);
			}
			
			log.info("Preparando respuesta");
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Consulta Exitosa");
			respuestaDto.setObj(listadoActivo);
			
			respuesta = new ResponseEntity<>(respuestaDto, HttpStatus.OK);			
		}catch(Exception e) {
			log.info("Exeption");
			return new ResponseEntity<>(new RespuestaGeneralWS("Error Inesperado", null), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return respuesta;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> setActivoFijo(ActivoFijoWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getTipoActivo() < 0) {
				log.info("Id tipo activo no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Id de tipo activo no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getId() < 0) {
				log.info("Id activo fijo no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Id activo fijo no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getNombre() == null || input.getNombre().isEmpty()) {
				log.info("Nombre activo fijo no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Nombre activo fijo no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getSerial() == null || input.getSerial().isEmpty()) {
				log.info("Serial no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Serial no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getNumeroInternoInventario() == null || input.getNumeroInternoInventario().isEmpty()) {
				log.info("Numero inventario no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Numero inventario no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getValorcompra() < 1) {
				log.info("Valor compra no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Valor Compra no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getFechaCompra() == null) {
				log.info("Fecha Compra no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Fecha compra no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			log.info("Buscando tipoActivo por Id");
			Optional<TipoActivo> tipoActivo = this.tipoActivoRepository.findById(input.getTipoActivo());
			if(!tipoActivo.isPresent()) {
				log.info("tipoActivo no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Tipo Activo no encontrada", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Realizando busqueda de activo fijo por Id");
			Optional<ActivoFijo> activoFijo = this.repository.findById(input.getId());
			
			if(!activoFijo.isPresent()) {
				log.info("Activo Fijo no encontrado");
				return new ResponseEntity<>(new RespuestaGeneralWS("Activo Fijo no encontrado", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Cargando datos");
			activoFijo.get().setNombre(input.getNombre());
			activoFijo.get().setTipoActivo(tipoActivo.get());
			activoFijo.get().setSerial(input.getSerial());
			activoFijo.get().setNumeroInternoInventario(input.getNumeroInternoInventario());
			activoFijo.get().setPeso(input.getPeso());
			activoFijo.get().setAlto(input.getAlto());
			activoFijo.get().setAncho(input.getAncho());
			activoFijo.get().setLargo(input.getLargo());
			activoFijo.get().setValorCompra(input.getValorcompra());
			activoFijo.get().setFechaCompra(new Date(input.getFechaCompra().getTime()));
			activoFijo.get().setEntregado(input.isEntregado()?1:0);
			activoFijo.get().setEstado(input.isEstado()?1:0);
			
			log.info("RegistrandoActivo");
			ActivoFijo nActivoFijo = this.repository.save(activoFijo.get());
			
			if(nActivoFijo == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Preparando respuesta");
			ActivoFijoWsOutput output = new ActivoFijoWsOutput();
			output.setId(nActivoFijo.getId());
			output.setNombre(nActivoFijo.getNombre());
			output.setTipoActivo(nActivoFijo.getTipoActivo().getDescripcion());
			output.setSerial(nActivoFijo.getSerial());
			output.setNumeroInternoInventario(nActivoFijo.getNumeroInternoInventario());
			output.setPeso(nActivoFijo.getPeso());
			output.setAlto(nActivoFijo.getAlto());
			output.setAncho(nActivoFijo.getAncho());
			output.setLargo(nActivoFijo.getLargo());
			output.setValorcompra(nActivoFijo.getValorCompra());
			output.setFechaCompra(new java.util.Date(nActivoFijo.getFechaCompra().getTime()));
			output.setEntregado(nActivoFijo.getEntregado()==1?true:false);
			output.setEstado(nActivoFijo.getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Consulta Exitosa");
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
	public ResponseEntity<RespuestaGeneralWS> setActivoFijoTipo(ActivoFijoWsInput input) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<RespuestaGeneralWS> addActivoFijo(ActivoFijoWsInput input) {
		ResponseEntity<RespuestaGeneralWS> respuesta = new ResponseEntity<>(new RespuestaGeneralWS(), HttpStatus.OK);
		try {
			if(input.getTipoActivo() < 0) {
				log.info("Id tipo activo no valido");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Id de tipo activo no valido");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getNombre() == null || input.getNombre().isEmpty()) {
				log.info("Nombre activo fijo no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Nombre activo fijo no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getSerial() == null || input.getSerial().isEmpty()) {
				log.info("Serial no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Serial no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getNumeroInternoInventario() == null || input.getNumeroInternoInventario().isEmpty()) {
				log.info("Numero inventario no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Numero inventario no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getValorcompra() < 1) {
				log.info("Valor compra no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Valor Compra no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			if(input.getFechaCompra() == null) {
				log.info("Fecha Compra no validos");
				RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
				respuestaDto.setDescripcion("Fecha compra no validos");
				return new ResponseEntity<>(respuestaDto, HttpStatus.BAD_REQUEST);
			}
			
			log.info("Buscando tipoActivo por Id");
			Optional<TipoActivo> tipoActivo = this.tipoActivoRepository.findById(input.getTipoActivo());
			if(!tipoActivo.isPresent()) {
				log.info("tipoActivo no encontrada");
				return new ResponseEntity<>(new RespuestaGeneralWS("Tipo Activo no encontrada", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Cargando datos de activo");
			ActivoFijo activoFijo = new ActivoFijo();
			activoFijo.setNombre(input.getNombre());
			activoFijo.setTipoActivo(tipoActivo.get());
			activoFijo.setSerial(input.getSerial());
			activoFijo.setNumeroInternoInventario(input.getNumeroInternoInventario());
			activoFijo.setPeso(input.getPeso());
			activoFijo.setAlto(input.getAlto());
			activoFijo.setAncho(input.getAncho());
			activoFijo.setLargo(input.getLargo());
			activoFijo.setValorCompra(input.getValorcompra());
			activoFijo.setFechaCompra(new Date(input.getFechaCompra().getTime()));
			activoFijo.setEntregado(input.isEntregado()?1:0);
			activoFijo.setEstado(input.isEstado()?1:0);
			
			log.info("RegistrandoActivo");
			ActivoFijo nActivoFijo = this.repository.save(activoFijo);
			
			if(nActivoFijo == null) {
				log.info("Error en la actualizacion");
				return new ResponseEntity<>(new RespuestaGeneralWS("Actualizacion no permitida", null), HttpStatus.NOT_FOUND);
			}
			
			log.info("Preparando respuesta");
			ActivoFijoWsOutput output = new ActivoFijoWsOutput();
			output.setId(nActivoFijo.getId());
			output.setNombre(nActivoFijo.getNombre());
			output.setTipoActivo(nActivoFijo.getTipoActivo().getDescripcion());
			output.setSerial(nActivoFijo.getSerial());
			output.setNumeroInternoInventario(nActivoFijo.getNumeroInternoInventario());
			output.setPeso(nActivoFijo.getPeso());
			output.setAlto(nActivoFijo.getAlto());
			output.setAncho(nActivoFijo.getAncho());
			output.setLargo(nActivoFijo.getLargo());
			output.setValorcompra(nActivoFijo.getValorCompra());
			output.setFechaCompra(new java.util.Date(nActivoFijo.getFechaCompra().getTime()));
			output.setEntregado(nActivoFijo.getEntregado()==1?true:false);
			output.setEstado(nActivoFijo.getEstado()==1?true:false);
			
			RespuestaGeneralWS respuestaDto = new RespuestaGeneralWS();
			respuestaDto.setDescripcion("Consulta Exitosa");
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
