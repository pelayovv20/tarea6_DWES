package com.alejandromg.tarea3dwes24.controllers;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErroresController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
    	Object status = request.getAttribute("javax.servlet.error.status_code");
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            switch (statusCode) {
                case 404:
                    return "error-404";
                case 400:
                    return "error-400";
                case 403:
                    return "error-403";
                case 500:
                    return "error-500";
            }
        }
        return "error";
}
}