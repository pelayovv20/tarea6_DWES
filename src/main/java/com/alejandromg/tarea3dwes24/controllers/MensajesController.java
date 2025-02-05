package com.alejandromg.tarea3dwes24.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alejandromg.tarea3dwes24.modelo.Ejemplar;
import com.alejandromg.tarea3dwes24.modelo.Mensaje;
import com.alejandromg.tarea3dwes24.modelo.Persona;
import com.alejandromg.tarea3dwes24.modelo.Planta;
import com.alejandromg.tarea3dwes24.servicios.Controlador;
import com.alejandromg.tarea3dwes24.servicios.ServiciosCredenciales;
import com.alejandromg.tarea3dwes24.servicios.ServiciosEjemplar;
import com.alejandromg.tarea3dwes24.servicios.ServiciosMensaje;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPersona;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;

@Controller
public class MensajesController {

    @Autowired
    private ServiciosMensaje servMensaje;
    
    @Autowired
    private ServiciosEjemplar servEjemplar;
    
    @Autowired
    private ServiciosPlanta servPlanta;
    
    @Autowired
    private ServiciosPersona servPersona;
    
    @Autowired
    private ServiciosCredenciales servCredenciales;
    
    @Autowired
    private Controlador controlador;

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
    	List<Mensaje> mensajes = new ArrayList<>();
    	mensajes = (List<Mensaje>) servMensaje.verTodos();
    	if (mensajes.isEmpty()) {
        	model.addAttribute("error", "No hay mensajes guardados");
        
    } else {
        model.addAttribute("mensajes", mensajes);
    }
    
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
model.addAttribute("plantas", servPlanta.verTodas());
    	
        if (codigoPlanta == null) {
        	return "listado_mensajes_planta";
        }
        Planta planta = servPlanta.buscarPorCodigo(codigoPlanta);
        if (planta==null) {
        	model.addAttribute("error", "El código de la planta introducida no existe en la base de datos");
        	return "listado_mensajes_planta";
        }
        
        List<Mensaje> mensajes = new ArrayList<>();
            mensajes = servMensaje.verMensajesPorCodigoPlanta(codigoPlanta);
           
            if (mensajes.isEmpty()) {
            	model.addAttribute("error", "No hay mensajes guardados para la planta " + planta.getCodigo());
            
        } else {
            model.addAttribute("mensajes", mensajes);
        }
        model.addAttribute("codigoPlanta", codigoPlanta);
        
        
        return "listado_mensajes_planta";
    }

    @GetMapping("/listado_mensajes_persona")
    public String listarMensajesPorPersona(@RequestParam(name = "idPersona", required = false) Long idPersona, Model model) {
model.addAttribute("personas", servPersona.verTodos());
    	
        if (idPersona == null) {
        	return "listado_mensajes_persona";
        }
        Optional<Persona> persona = servPersona.buscarPorId(idPersona);
        if (persona==null) {
        	model.addAttribute("error", "El id de la persona introducida no existe en la base de datos");
        	return "listado_mensajes_persona";
        }
        
        List<Mensaje> mensajes = new ArrayList<>();
            mensajes = servMensaje.verMensajesPorPersona(idPersona);
           
            if (mensajes.isEmpty()) {
            	model.addAttribute("error", "No hay mensajes guardados para la persona " + persona.get().getCredenciales().getUsuario() );
            
        } else {
            model.addAttribute("mensajes", mensajes);
        }
        model.addAttribute("idPersona", idPersona);
        
        
        return "listado_mensajes_persona";
    }

    @GetMapping("/listado_mensajes_fechas")
    public String listarMensajesPorFechas(@RequestParam(name = "fechaInicio", required = false) String fecha1,
    									 @RequestParam(name = "fechaFin", required = false) String fecha2, Model model) {


    		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		LocalDateTime fechaInicio = null;
    		LocalDateTime fechaFin = null;


    		if (fecha1 != null && !fecha1.isEmpty()) {
    			
    				LocalDate fechaInicioCorrecta = LocalDate.parse(fecha1, formatter);
    				fechaInicio = fechaInicioCorrecta.atStartOfDay();
    			
    		}

    		if (fecha2 != null && !fecha2.isEmpty()) {
    			
    				LocalDate fechaFinCorrecta = LocalDate.parse(fecha2, formatter);
    				fechaFin = fechaFinCorrecta.atStartOfDay();
    				if (fechaFin.isBefore(fechaInicio)) {
    					model.addAttribute("error", "La fecha de fin no puede ser anterior a la fecha de inicio");
    					return "listado_mensajes_fechas";
    				}
    			
    		}

    		if (fechaInicio != null && fechaFin != null) {
    			List<Mensaje> mensajes = servMensaje.verMensajesRangoFechas(fechaInicio, fechaFin);
    			if (mensajes.isEmpty()) {
    				model.addAttribute("error", "No se encontraron mensajes en el rango de fechas proporcionado");
    			} else {
    				model.addAttribute("mensajes", mensajes);
    			}
    		} else if (fecha1 != null || fecha2 != null) {

    			model.addAttribute("error", "Ambas fechas son necesarias");
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
            Ejemplar ejemplar = servEjemplar.buscarPorID(idEjemplar);
            if (ejemplar == null) {
                model.addAttribute("error", "No se encontró un ejemplar con el ID: " + idEjemplar);
            }else {

            Mensaje nuevoMensaje = new Mensaje();
            String usuarioAutenticado = controlador.getUsuarioAutenticado();
            Persona persona = servCredenciales.buscarPersonaPorUsuario(usuarioAutenticado);
            nuevoMensaje.setEjemplar(ejemplar);
            nuevoMensaje.setFechaHora(LocalDateTime.now());
            nuevoMensaje.setMensaje(contenido);
            nuevoMensaje.setPersona(persona);
            servMensaje.insertar(nuevoMensaje);

            model.addAttribute("mensaje", "Mensaje insertado");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al insertar el mensaje");
        }
        model.addAttribute("ejemplares", servEjemplar.verTodos());
        return "insertar_mensaje";
    }

}

