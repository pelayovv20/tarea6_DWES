package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErroresController implements ErrorController {

    @RequestMapping("/error")
    public String manejoErrores(HttpServletRequest request, Model model) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        if (statusCode != null) {
            model.addAttribute("status", statusCode.toString());
        }
        if (errorMessage != null) {
            model.addAttribute("message", errorMessage.toString());
        }
        if (requestUri != null) {
            model.addAttribute("path", requestUri.toString());
        }
        model.addAttribute("error", "Algo salió mal, vuelve a la página anterior");

        return "error";
    }
}
