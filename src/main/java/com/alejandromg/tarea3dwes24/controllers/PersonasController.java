package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alejandromg.tarea3dwes24.servicios.ServiciosCredenciales;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPersona;

@Controller
public class PersonasController {
	@Autowired
	private ServiciosPersona servPersona;
	@Autowired
	private ServiciosCredenciales servCredenciales;
	
	
	
}
