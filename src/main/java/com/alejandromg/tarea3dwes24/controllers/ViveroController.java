package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.alejandromg.tarea3dwes24.modelo.Credenciales;

@Controller
public class ViveroController {
	
	 @GetMapping("/")
	 public String invitado() {
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
	 public String procesoLogin(@ModelAttribute Credenciales credenciales, Model model) {
	     if ("admin".equals(credenciales.getUsuario()) && "admin".equals(credenciales.getPassword())) {
	         model.addAttribute("mensaje", "¡Bienvenido, " + credenciales.getUsuario() + "!");
	         return "bienvenida";
	     } else {
	         model.addAttribute("error", "Usuario o contraseña incorrectos");
	         return "login";
	     }
	 }

}
