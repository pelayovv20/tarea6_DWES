package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.alejandromg.tarea3dwes24.servicios.ServiciosEjemplar;

@Controller
public class EjemplaresController {
	@Autowired
	private ServiciosEjemplar servEjemplar;
	
	@GetMapping("/ejemplares")
    public String listarEjemplares(Model model) {
        model.addAttribute("ejemplares", servEjemplar.verTodos());
        return "listado_ejemplares";
    }
}
