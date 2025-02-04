package com.alejandromg.tarea3dwes24.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;

@Controller
public class MensajesController {

    @Autowired
    private ServiciosMensaje servMensaje;
    
    @Autowired
    private ServiciosEjemplar servEjemplar;
    
    @Autowired
    private ServiciosPlanta servPlanta;

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
    	model.addAttribute("ejemplares", servEjemplar.verTodos());
    	
        if (idEjemplar == null) {
        	return "listado_mensajes_ejemplar";
        }
        Ejemplar ejemplar = servEjemplar.buscarPorID(idEjemplar);
        if (ejemplar==null) {
        	model.addAttribute("error", "El id del ejemplar introducido no existe en la base de datos");
        	return "listado_mensajes_ejemplar";
        }
        
        List<Mensaje> mensajes = new ArrayList<>();
            mensajes = servMensaje.verMensajesPorEjemplar(idEjemplar).stream().filter(m -> m.getEjemplar() != null && idEjemplar.equals(m.getEjemplar().getId())).toList();
           
            if (mensajes.isEmpty()) {
            	model.addAttribute("error", "No hay mensajes guardados para el ejemplar " + ejemplar.getNombre());
            
        } else {
            model.addAttribute("mensajes", mensajes);
        }
        model.addAttribute("idEjemplar", idEjemplar);
        
        
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
        model.addAttribute("ejemplares", servEjemplar.verTodos()); 
        return "insertar_mensaje";
    }

    @PostMapping("/insertar_mensaje")
    public String insertarMensaje(@RequestParam("idEjemplar") Long idEjemplar,
                                  @RequestParam("contenido") String contenido,
                                  Model model) {
        try {
            if (idEjemplar == null || contenido == null || contenido.trim().isEmpty()) {
                model.addAttribute("error", "Tienes que completar todos los campos para poner el mensaje");
                return "insertar_mensaje";
            }

            Ejemplar ejemplar = servEjemplar.buscarPorID(idEjemplar);
            if (ejemplar == null) {
                model.addAttribute("error", "No se encontró un ejemplar con el ID: " + idEjemplar);
                return "insertar_mensaje";
            }

            Mensaje nuevoMensaje = new Mensaje();
            nuevoMensaje.setEjemplar(ejemplar);
            nuevoMensaje.setFechaHora(LocalDateTime.now());
            nuevoMensaje.setMensaje(contenido);
            servMensaje.insertar(nuevoMensaje);

            model.addAttribute("mensaje", "Mensaje insertado");
        } catch (Exception e) {
            model.addAttribute("error", "Error al insertar el mensaje");
        }
        model.addAttribute("ejemplares", servEjemplar.verTodos());
        return "insertar_mensaje";
    }

}

