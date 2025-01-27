package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErroresController implements ErrorController {

    @GetMapping("/error")
    public String manejar404() {
        return "error-404";
    }
    
    @GetMapping("/error")
    public String manejar400() {
        return "error-400";
    }
    @GetMapping("/error")
    public String manejar403() {
        return "error-403";
    }
    
    @GetMapping("/error")
    public String manejar500() {
        return "error-500";
    }

}