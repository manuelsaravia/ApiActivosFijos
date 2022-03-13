package com.activos.fijos.ApiActivosFijos.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.activos.fijos.ApiActivosFijos.entity.Ciudad;
import com.activos.fijos.ApiActivosFijos.repository.CiudadRepository;

@RestController
@RequestMapping("/ciudad")
public class CiudadController {

	private static final Logger log = LoggerFactory.getLogger(CiudadController.class);
	
	@Autowired
	private CiudadRepository ciudadRepository;
	
	@GetMapping("/getCiudad")
	public Ciudad getCiudad(@RequestParam int id) {
		return this.ciudadRepository.findById(id).get();
	}
	
}
