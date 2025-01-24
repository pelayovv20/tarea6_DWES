package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;

@Controller
public class PlantasController {
	
	@Autowired
	private ServiciosPlanta servPlanta;

}
