package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.alejandromg.tarea3dwes24.servicios.ServiciosCredenciales;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPersona;

@Controller
public class PersonasController {
	@Autowired
	private ServiciosPersona servPersona;
	@Autowired
	private ServiciosCredenciales servCredenciales;
	
	@GetMapping("/personas")
    public String listarPersonas(Model model) {
        model.addAttribute("personas", servPersona.verTodos());
        return "listado_personas";
    }
	

	@GetMapping("/gestion_personas")
	public String gestionPersonas() {
		return "gestion_personas";
	    }

	
}
