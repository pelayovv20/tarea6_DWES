package com.alejandromg.tarea3dwes24.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alejandromg.tarea3dwes24.modelo.Credenciales;
import com.alejandromg.tarea3dwes24.modelo.Persona;
import com.alejandromg.tarea3dwes24.servicios.ServiciosCredenciales;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPersona;

@Controller
public class PersonasController {
	@Autowired
	private ServiciosPersona servPersona;
	@Autowired
	private ServiciosCredenciales servCredenciales;

	@GetMapping("/listado_personas")
	public String listarPersonas(Model model) {
	    List<Persona> personas = (List<Persona>) servPersona.verTodos();
	    for (Persona persona : personas) {
	        if (persona.getCredenciales() != null) {
	            String usuario = persona.getCredenciales().getUsuario(); 
	        }
	    }
	    model.addAttribute("personas", personas);
	    return "listado_personas";
	}


    @GetMapping("/gestion_personas")
    public String gestionPersonas() {
        return "gestion_personas";
    }
    @GetMapping("/insertar_persona")
    public String mostrarFormulario(Model model) {
        model.addAttribute("persona", new Persona());
        return "insertar_persona";
    }

    @PostMapping("/insertar_persona")
    public String insertarPersona(@ModelAttribute Persona persona, @RequestParam("usuario") String usuario, @RequestParam("password") String password, Model model) {
        try {
            servPersona.insertar(persona);
            Credenciales credenciales = new Credenciales();
            credenciales.setUsuario(usuario);
            credenciales.setPassword(password);
            credenciales.setPersona(persona); 
            servCredenciales.insertar(usuario, password, persona.getId());
            model.addAttribute("mensaje", "Persona y sus credenciales insertadas");
        } catch (Exception e) {
            model.addAttribute("error", "Error al insertar persona: " + e.getMessage());
        }

        return "redirect:/gestion_personas";
    }

}
