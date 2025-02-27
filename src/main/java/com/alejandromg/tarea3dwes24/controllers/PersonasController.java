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
import com.alejandromg.tarea3dwes24.servicios.Controlador;
import com.alejandromg.tarea3dwes24.servicios.PerfilUsuario;
import com.alejandromg.tarea3dwes24.servicios.ServiciosCredenciales;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPersona;

@Controller
public class PersonasController {
	@Autowired
	private ServiciosPersona servPersona;
	@Autowired
	private ServiciosCredenciales servCredenciales;
	
	@Autowired
	private Controlador controlador;

	@GetMapping("/listado_personas")
	public String listarPersonas(Model model) {
		if (controlador.getPerfil() == null || controlador.getPerfil() != PerfilUsuario.ADMIN) {
    	    return "/acceso_perfiles";
    	}
	    List<Persona> personas = (List<Persona>) servPersona.verTodos();
	    for (Persona persona : personas) {
	        if (persona.getCredenciales() != null) {
	            String usuario = persona.getCredenciales().getUsuario();
	            String contraseña = persona.getCredenciales().getPassword();
	            String contraseñaOculta = "*".repeat(contraseña.length());
	            persona.getCredenciales().setPassword(contraseñaOculta);
	        }
	    }
	    model.addAttribute("personas", personas);
	    return "listado_personas";
	}


    @GetMapping("/gestion_personas")
    public String gestionPersonas() {
    	if (controlador.getPerfil() == null || controlador.getPerfil() != PerfilUsuario.ADMIN) {
    	    return "/acceso_perfiles";
    	}
        return "gestion_personas";
    }
    
    @GetMapping("/insertar_persona")
    public String mostrarFormulario(Model model) {
    	if (controlador.getPerfil() == null || controlador.getPerfil() != PerfilUsuario.ADMIN) {
    	    return "/acceso_perfiles";
    	}	
        model.addAttribute("persona", new Persona());
        return "insertar_persona";
    }

    @PostMapping("/insertar_persona")
    public String insertarPersona(@ModelAttribute Persona persona, @RequestParam("usuario") String usuario, @RequestParam("password") String password, Model model) {
        try {
           
            Credenciales credenciales = new Credenciales();
            credenciales.setUsuario(usuario);
            credenciales.setPassword(password);
            credenciales.setPersona(persona); 
            
            	if (servCredenciales.usuarioExistente(usuario)) {
            		model.addAttribute("error", "El nombre de usuario ya existe, Por favor introduce otro");
            	}else if(usuario.length()<3 || usuario.contains(" ")) {
            		model.addAttribute("error", "Nombre de usuario incorrecto. Por favor introduce un nombre de usuario válido");
            	}else {
            		if (servCredenciales.validarContraseña(password)) {
                		servPersona.insertar(persona);
                		servCredenciales.insertar(usuario, password, persona.getId());
                		 model.addAttribute("mensaje", "Persona y sus credenciales insertadas");
                	}else {
                		model.addAttribute("error", "Contraseña incorrecta. Por favor introduce una contraseña válida");
                	}
            		
            	}
            	
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al insertar la persona");
        }
        return "insertar_persona";
    }

}
