package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.alejandromg.tarea3dwes24.servicios.ServiciosMensaje;

@Controller
public class MensajesController {

	@Autowired
	private ServiciosMensaje servMensaje;
	
	@GetMapping("/mensajes")
    public String listarMensajes(Model model) {
        model.addAttribute("mensajes", servMensaje.verTodos());
        return "listado_mensajes";
    }
	
	@GetMapping("/gestion_mensajes")
	public String gestionMensajes() {
	    return "gestion_mensajes";
	}
	
	
}
