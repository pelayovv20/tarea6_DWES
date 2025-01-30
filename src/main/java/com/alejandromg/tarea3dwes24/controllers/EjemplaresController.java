package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alejandromg.tarea3dwes24.servicios.ServiciosEjemplar;

@Controller
public class EjemplaresController {
	@Autowired
	private ServiciosEjemplar servEjemplar;
	
	@GetMapping("/ejemplares")
	public String listarEjemplares(@RequestParam(required = false) String codigoPlanta, Model model) {
	    if (codigoPlanta != null && !codigoPlanta.isEmpty()) {
	        model.addAttribute("ejemplares", servEjemplar.ejemplaresPorTipoPlanta(codigoPlanta));
	    } else {
	        model.addAttribute("ejemplares", servEjemplar.verTodos());
	    }
	    return "listado_ejemplares";
	}
	
	@GetMapping("/gestion_ejemplares")
	public String gestionEjemplares() {
	    return "gestion_ejemplares";
	}

}

