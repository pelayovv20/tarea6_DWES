package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alejandromg.tarea3dwes24.modelo.Ejemplar;
import com.alejandromg.tarea3dwes24.modelo.Mensaje;
import com.alejandromg.tarea3dwes24.servicios.ServiciosEjemplar;
import com.alejandromg.tarea3dwes24.servicios.ServiciosMensaje;

@Controller
public class MensajesController {

    @Autowired
    private ServiciosMensaje servMensaje;

    @Autowired
    private ServiciosEjemplar servEjemplar;
    
    @GetMapping("/gestion_mensajes")
    public String gestionMensajes() {
        return "gestion_mensajes";
    }

    @GetMapping("/menu_listado_mensajes")
    public String listarMensajes(Model model) {
        model.addAttribute("mensajes", servMensaje.verTodos());
        return "listado_mensajes";
    }

    @GetMapping("/insertar_mensaje")
    public String mostrarFormulario(Model model) {
        model.addAttribute("mensaje", new Mensaje());
        model.addAttribute("ejemplares", servEjemplar.verTodos());
        return "insertar_mensaje";
    }

    @PostMapping("/insertar_mensaje")
    public String insertarMensaje(@RequestParam("idEjemplar") Long idEjemplar, @RequestParam("contenido") String contenido, Model model) {
        try {
            Ejemplar ejemplar = servEjemplar.buscarPorID(idEjemplar);
            if (ejemplar == null) {
                model.addAttribute("error", "El ejemplar con ID " + idEjemplar + " no existe");
            } else {
                Mensaje nuevoMensaje = new Mensaje();
                nuevoMensaje.setEjemplar(ejemplar);
                nuevoMensaje.setMensaje(contenido);
                servMensaje.insertar(nuevoMensaje);
                model.addAttribute("mensaje", "Mensaje insertado");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al insertar el mensaje");
        }
        model.addAttribute("mensajes", servMensaje.verTodos());
        return "insertar_mensaje";
    }
}
