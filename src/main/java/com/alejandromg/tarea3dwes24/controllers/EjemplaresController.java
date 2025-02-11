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
import com.alejandromg.tarea3dwes24.modelo.Persona;
import com.alejandromg.tarea3dwes24.modelo.Planta;
import com.alejandromg.tarea3dwes24.servicios.Controlador;
import com.alejandromg.tarea3dwes24.servicios.ServiciosCredenciales;
import com.alejandromg.tarea3dwes24.servicios.ServiciosEjemplar;
import com.alejandromg.tarea3dwes24.servicios.ServiciosMensaje;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;

@Controller
public class EjemplaresController {
	@Autowired
	private ServiciosEjemplar servEjemplar;
	
	@Autowired
	private ServiciosPlanta servPlanta;
	
	@Autowired
	private ServiciosCredenciales servCredenciales;
	
	@Autowired
	private ServiciosMensaje servMensaje;
	
	@Autowired
	private Controlador controlador;
	
	@GetMapping("/gestion_ejemplares")
    public String gestionEjemplares() {
        return "gestion_ejemplares";
    }
	@GetMapping("/listado_ejemplares")
	public String listadoEjemplares(@RequestParam(value = "codigoPlanta", required = false) String codigoPlanta, Model model) {
	    model.addAttribute("plantas", servPlanta.verTodas());
	    if (codigoPlanta == null || codigoPlanta.isEmpty()) {
	        
	        return "listado_ejemplares";
	    }
	    Planta planta = servPlanta.buscarPorCodigo(codigoPlanta);
	    if (planta == null) {
	        model.addAttribute("error", "El código " + codigoPlanta + " no existe en el vivero");
	        return "listado_ejemplares";
	    }
	    List<Ejemplar> ejemplares = (List<Ejemplar>) servEjemplar.verTodos();
	    List<Ejemplar> ejemplaresFiltrados = ejemplares.stream().filter(e -> e.getPlanta().getCodigo().equalsIgnoreCase(codigoPlanta)).toList();
	    if (ejemplaresFiltrados.isEmpty()) {
	        model.addAttribute("error", "No hay ejemplares guardados para la planta con código: " + codigoPlanta);
	    } else {
	        model.addAttribute("ejemplares", ejemplaresFiltrados);
	    }
	    model.addAttribute("codigoPlanta", codigoPlanta);
	    return "listado_ejemplares";
	}


    @GetMapping("/insertar_ejemplar")
    public String mostrarFormularioInsertar(Model model) {
        model.addAttribute("plantas", servPlanta.verTodas());
        model.addAttribute("ejemplar", new Ejemplar());
        return "insertar_ejemplar";
    }

    @PostMapping("/insertar_ejemplar")
    public String insertarEjemplar(@RequestParam("codigo") String codigoPlanta, Model model) {
        try {
            Planta planta = servPlanta.buscarPorCodigo(codigoPlanta);
            if (planta == null) {
                model.addAttribute("error", "La planta con código " + codigoPlanta + " no existe");
            } else {
                Ejemplar nuevoEjemplar = new Ejemplar();
                nuevoEjemplar.setPlanta(planta);
                nuevoEjemplar.setNombre(codigoPlanta); 
                servEjemplar.insertar(nuevoEjemplar);
                nuevoEjemplar.setNombre(nuevoEjemplar.getPlanta().getCodigo() + "_" + nuevoEjemplar.getId());
                servEjemplar.cambiarNombre(nuevoEjemplar.getId(), nuevoEjemplar.getNombre());
                Mensaje m = new Mensaje();
                String mensajeTexto = "Añadido el ejemplar " + nuevoEjemplar.getNombre();
	            LocalDateTime fechaHora = LocalDateTime.now();
	            String usuarioAutenticado = controlador.getUsuarioAutenticado();
	            Persona persona = servCredenciales.buscarPersonaPorUsuario(usuarioAutenticado);
	            if (persona == null) {
	                model.addAttribute("error", "No se ha encontrado la persona");
	            } else {
	                m = new Mensaje(fechaHora, mensajeTexto, persona, nuevoEjemplar); //Inserto un mensaje de creación del ejemplar
	                servMensaje.insertar(m);
	                System.out.println("Mensaje de creación del ejemplar añadido");
	            }
                model.addAttribute("mensaje", "Ejemplar insertado correctamente");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al insertar ejemplar: " + e.getMessage());
        }

        model.addAttribute("plantas", servPlanta.verTodas());
        return "insertar_ejemplar";
    }




}

