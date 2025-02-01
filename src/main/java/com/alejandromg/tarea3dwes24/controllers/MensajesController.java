package com.alejandromg.tarea3dwes24.controllers;

import java.time.LocalDateTime;

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
    public String listarMensajesPorEjemplar(@RequestParam("idEjemplar") Long idEjemplar, Model model) {
        model.addAttribute("mensajes", servMensaje.verMensajesPorEjemplar(idEjemplar));
        return "listado_mensajes_ejemplar";
    }

    @GetMapping("/listado_mensajes_planta")
    public String listarMensajesPorPlanta(@RequestParam("codigoPlanta") String codigoPlanta, Model model) {
        model.addAttribute("mensajes", servMensaje.verMensajesPorCodigoPlanta(codigoPlanta));
        return "listado_mensajes_planta";
    }

    @GetMapping("/listado_mensajes_persona")
    public String listarMensajesPorPersona(@RequestParam("idPersona") Long idPersona, Model model) {
        model.addAttribute("mensajes", servMensaje.verMensajesPorPersona(idPersona));
        return "listado_mensajes_persona";
    }

    @GetMapping("/listado_mensajes_fechas")
    public String listarMensajesPorFechas(@RequestParam("fechaInicio") LocalDateTime fechaInicio,
                                          @RequestParam("fechaFin") LocalDateTime fechaFin, Model model) {
        model.addAttribute("mensajes", servMensaje.verMensajesRangoFechas(fechaInicio, fechaFin));
        return "listado_mensajes_fechas";
    }
    
    @GetMapping("/insertar_mensaje")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ejemplares", servEjemplar.verTodos()); // Lista de ejemplares disponibles
        return "insertar_mensaje";
    }

    /**
     * Inserta un mensaje en la base de datos.
     */
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
                model.addAttribute("mensaje", "Mensaje insertado correctamente.");
            } else {
                model.addAttribute("error", "No se encontró el ejemplar con ID: " + idEjemplar);
            }

        } catch (Exception e) {
            model.addAttribute("error", "Error al insertar el mensaje.");
        }

        model.addAttribute("mensajes", servMensaje.verTodos()); // Refresca la lista de mensajes
        return "insertar_mensaje";
    }
}
