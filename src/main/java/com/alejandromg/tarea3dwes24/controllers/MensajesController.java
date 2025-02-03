package com.alejandromg.tarea3dwes24.controllers;

import java.time.LocalDateTime;
import java.util.List;

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
    public String menuListadoMensajes() {
        return "menu_listado_mensajes";
    }

    @GetMapping("/listado_todos_mensajes")
    public String listarTodosLosMensajes(Model model) {
        model.addAttribute("mensajes", servMensaje.verTodos());
        return "listado_todos_mensajes";
    }

    @GetMapping("/listado_mensajes_ejemplar")
    public String listarMensajesPorEjemplar(@RequestParam(name = "idEjemplar", required = false) Long idEjemplar, Model model) {
        if (idEjemplar != null) {
            List<Mensaje> mensajes = servMensaje.verMensajesPorEjemplar(idEjemplar);
            model.addAttribute("mensajes", mensajes);
            model.addAttribute("idEjemplar", idEjemplar);
        } else {
            model.addAttribute("error", "Debes introducir un ID de ejemplar");
        }
        return "listado_mensajes_ejemplar";
    }

    @GetMapping("/listado_mensajes_planta")
    public String listarMensajesPorPlanta(@RequestParam(name = "codigoPlanta", required = false) String codigoPlanta, Model model) {
        if (codigoPlanta != null && !codigoPlanta.isEmpty()) {
            List<Mensaje> mensajes = servMensaje.verMensajesPorCodigoPlanta(codigoPlanta);
            model.addAttribute("mensajes", mensajes);
            model.addAttribute("codigoPlanta", codigoPlanta);
        } else {
            model.addAttribute("error", "Debes introducir un código de planta");
        }
        return "listado_mensajes_planta";
    }

    @GetMapping("/listado_mensajes_persona")
    public String listarMensajesPorPersona(@RequestParam(name = "idPersona", required = false) Long idPersona, Model model) {
        if (idPersona != null) {
            List<Mensaje> mensajes = servMensaje.verMensajesPorPersona(idPersona);
            model.addAttribute("mensajes", mensajes);
            model.addAttribute("idPersona", idPersona);
        } else {
            model.addAttribute("error", "Debes introducir un ID de persona");
        }
        return "listado_mensajes_persona";
    }

    @GetMapping("/listado_mensajes_fechas")
    public String listarMensajesPorFechas(@RequestParam(name = "fechaInicio", required = false) LocalDateTime fechaInicio,
                                          @RequestParam(name = "fechaFin", required = false) LocalDateTime fechaFin, Model model) {
        if (fechaInicio != null && fechaFin != null) {
            List<Mensaje> mensajes = servMensaje.verMensajesRangoFechas(fechaInicio, fechaFin);
            model.addAttribute("mensajes", mensajes);
            model.addAttribute("fechaInicio", fechaInicio);
            model.addAttribute("fechaFin", fechaFin);
        } else {
            model.addAttribute("error", "Debes seleccionar un rango de fechas válido");
        }
        return "listado_mensajes_fechas";
    }
    
    @GetMapping("/insertar_mensaje")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ejemplares", servEjemplar.verTodos()); // Lista de ejemplares disponibles
        return "insertar_mensaje";
    }

    @PostMapping("/insertar_mensaje")
    public String insertarMensaje(@RequestParam("idEjemplar") Long idEjemplar, 
                                  @RequestParam("contenido") String contenido, 
                                  Model model) {
        try {
            Ejemplar ejemplar = servEjemplar.buscarPorID(idEjemplar);
            if (ejemplar != null) {
                Mensaje nuevoMensaje = new Mensaje();
                nuevoMensaje.setEjemplar(ejemplar);
                nuevoMensaje.setMensaje(contenido);
                servMensaje.insertar(nuevoMensaje);
                model.addAttribute("mensaje", "Mensaje insertado");
            } else {
                model.addAttribute("error", "No se encontró el ejemplar con ID: " + idEjemplar);
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al insertar el mensaje");
        }
        model.addAttribute("mensajes", servMensaje.verTodos());
        return "/insertar_mensaje";
    }
}

