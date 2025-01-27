package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViveroController {
	 @GetMapping("/")
	 public String invitado() {
	    return "invitado";
	    }
}
