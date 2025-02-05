package com.alejandromg.tarea3dwes24.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.alejandromg.tarea3dwes24.modelo.Credenciales;
import com.alejandromg.tarea3dwes24.servicios.Controlador;
import com.alejandromg.tarea3dwes24.servicios.PerfilUsuario;
import com.alejandromg.tarea3dwes24.servicios.ServiciosCredenciales;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPersona;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;

@Controller
public class ViveroController {
	
	@Autowired
	private ServiciosPersona servPersona;
	
	@Autowired
	private ServiciosPlanta servPlanta;
	@Autowired
	private ServiciosCredenciales servCred;
	
	@Autowired
	private Controlador controlador;
	
	 @GetMapping("/")
	 public String invitado(Model model) {
			 model.addAttribute("plantas",servPlanta.verTodas());
	    return "invitado";
	    }
	
	 @GetMapping("/login")
	    public String formularioLogin(Model model) {
	        model.addAttribute("credenciales", new Credenciales());
	        return "login";
	    }

	 @PostMapping("/login")
	 public String iniciarSesion(@ModelAttribute Credenciales credenciales, Model model) {
	     String usuario = credenciales.getUsuario();
	     String password = credenciales.getPassword();
	     
	     try {
	         boolean autenticar = servCred.autenticar(usuario, password);
	         if (autenticar) {
	             long id = servPersona.IdUsuarioAutenticado(usuario);
	             PerfilUsuario perfil;

	             if ("admin".equalsIgnoreCase(usuario)) {
	                 perfil = PerfilUsuario.ADMIN;
	                 
	             } else {
	                 perfil = PerfilUsuario.PERSONAL;
	             }
	             controlador.setUsuarioAutenticado(usuario);
	             controlador.iniciarSesion(id, usuario, perfil, LocalDateTime.now());
	             return "menuPrincipal";
	             
	         } else {
	             model.addAttribute("error", "Usuario o contraseña incorrectos");
	             return "login";
	         }
	         
	         
	     } catch (Exception e) {
	         model.addAttribute("error", "No se ha podido iniciar sesión");
	         return "login";
	     }
	 }
	 
	 @GetMapping("/logout")
	 public String cerrarSesion() {
	     controlador.cerrarSesion();
	     return "invitado";
	 }
	 
	 @GetMapping("/menu")
	    public String mostrarMenu(Model model) {
	        String usuario = controlador.getUsuarioAutenticado();
	        if (usuario == null || usuario.isEmpty()) {
	            return "redirect:/login";
	        }
	        model.addAttribute("usuarioAutenticado", usuario);
	        model.addAttribute("perfil", controlador.getPerfil());
	        return "menuPrincipal";
	    }

}
