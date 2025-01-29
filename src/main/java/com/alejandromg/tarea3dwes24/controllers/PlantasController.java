package com.alejandromg.tarea3dwes24.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alejandromg.tarea3dwes24.modelo.Planta;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;

@Controller
public class PlantasController {
	
	@Autowired
	private ServiciosPlanta servPlanta;
	
	@GetMapping("/listado_plantas")
    public String listarPlantas(Model model) {
        model.addAttribute("plantas", servPlanta.verTodas());
        return "listado_plantas";
    }
	

	@GetMapping("/gestion_plantas")
	public String gestionPlantas() {
	    return "gestion_plantas";
	}
	
	@GetMapping("/menu_modificar_plantas")
	public String modificarPlanta() {
	    return "menu_modificar_plantas";
	}

	    @GetMapping("/insertar_planta")
	    public String mostrarFormularioInsertarPlanta() {
	        return "insertar_planta";
	    }

	    @PostMapping("/insertar_planta")
	    public String insertarPlanta(@RequestParam("codigo") String codigo,@RequestParam("nombreComun") String nombreComun,@RequestParam("nombreCientifico") String nombreCientifico,Model model) {
	        try {
	            Planta p = new Planta(codigo, nombreComun, nombreCientifico);
	            servPlanta.insertar(p);
	            model.addAttribute("mensaje", "Planta insertada");
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al insertar la planta: " + e.getMessage());
	        }
	        return "/gestion_plantas";
	    }
	    @PostMapping("/modificar_nombre_comun")
	    public String modificarNombreComun(@RequestParam("codigo") String codigo, @RequestParam("nombreComun") String nuevoNombreComun, Model model) {
	        try {
	            servPlanta.actualizarNombreComun(codigo, nuevoNombreComun);
	            model.addAttribute("mensaje", "Nombre común modificado");
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al modificar el nombre común: " + e.getMessage());
	        }
	        model.addAttribute("plantas", servPlanta.verTodas());
	        return "modificar_nombreComun_planta";
	    }

	    @PostMapping("/modificar_nombre_cientifico")
	    public String modificarNombreCientifico(@RequestParam("codigo") String codigo,@RequestParam("nombreCientifico") String nuevoNombreCientifico,Model model) {
	        try {
	            servPlanta.actualizarNombreCientifico(codigo, nuevoNombreCientifico);
	            model.addAttribute("mensaje", "Nombre científico modificado");
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al modificar el nombre científico: " + e.getMessage());
	        }
	        model.addAttribute("plantas", servPlanta.verTodas());
	        return "modificar_nombreCientifico_planta";
	    }

}
