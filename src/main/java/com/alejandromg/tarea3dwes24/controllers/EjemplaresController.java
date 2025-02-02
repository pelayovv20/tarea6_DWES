package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alejandromg.tarea3dwes24.modelo.Ejemplar;
import com.alejandromg.tarea3dwes24.modelo.Planta;
import com.alejandromg.tarea3dwes24.servicios.ServiciosEjemplar;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;

@Controller
public class EjemplaresController {
	@Autowired
	private ServiciosEjemplar servEjemplar;
	
	@Autowired
	private ServiciosPlanta servPlanta;
	
	@GetMapping("/gestion_ejemplares")
    public String gestionEjemplares() {
        return "gestion_ejemplares";
    }
    @GetMapping("/listado_ejemplares")
    public String listadoEjemplares(@RequestParam(value = "codigoPlanta", required = false) String codigoPlanta, Model model) {
        model.addAttribute("plantas", servPlanta.verTodas());
        if (servPlanta.validarCodigo(codigoPlanta)) {
            model.addAttribute("ejemplares", servEjemplar.ejemplaresPorPlanta(codigoPlanta));
            model.addAttribute("codigoPlanta", codigoPlanta);
        }
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
                model.addAttribute("mensaje", "Ejemplar insertado correctamente");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error al insertar ejemplar: " + e.getMessage());
        }

        model.addAttribute("plantas", servPlanta.verTodas());
        return "insertar_ejemplar";
    }




}

