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

import jakarta.servlet.http.HttpSession;

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
			 model.addAttribute("listadoPlantas",servPlanta.verTodas());
		 
	    return "invitado";
	    }
	 
	 
	 
	 @GetMapping("/salir")
	    public String salir() {
	        return "salir"; 
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
	             controlador.iniciarSesion(id, usuario, perfil, LocalDateTime.now());
	             if (perfil == PerfilUsuario.ADMIN) {
	            	 
	                 return "/menuAdmin";
	                 
	             } else  {
	                 return "/menuPersonal";
	             }
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
	     return "/invitado";
	 }
	 
	 @GetMapping("/admin")
	 public String admin() {
	     return "menuAdmin";
	 }

	 @GetMapping("/personal")
	 public String personal(Model model) {
		 System.out.println("USUARIO EN SESIÓN: " + controlador.getUsuarioAutenticado()); // <-- Debug
		    if (controlador.getUsuarioAutenticado() != null) {
		        model.addAttribute("usuarioAutenticado", controlador.getUsuarioAutenticado());
		    } else {
		        model.addAttribute("usuarioAutenticado", "Usuario no autenticado");
		    }
		    return "menuPersonal";
		}
	 
	 @GetMapping("/volver")
	 public String volver(HttpSession session) {
		 String pagina = (String) session.getAttribute("pagina");
		 
		 
		 if ("admin".equalsIgnoreCase(pagina)) {
			 return "/menuAdmin";
		 }else if ("personal".equalsIgnoreCase(pagina)) {
			 return "/menuPersonal";
		 }else {
			 return "/error-400";
		 }
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

}
