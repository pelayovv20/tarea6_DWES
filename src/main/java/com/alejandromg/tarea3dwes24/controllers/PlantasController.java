package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;

@Controller
public class PlantasController {
	
	@Autowired
	private ServiciosPlanta servPlanta;
	
	@GetMapping("/plantas")
    public String listarPlantas(Model model) {
        model.addAttribute("plantas", servPlanta.verTodas());
        return "listado_plantas";
    }
	

	@GetMapping("/gestion_plantas")
	public String gestionPlantas() {
	    return "gestion_plantas";
	}
}
