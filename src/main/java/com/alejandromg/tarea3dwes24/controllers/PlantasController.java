package com.alejandromg.tarea3dwes24.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alejandromg.tarea3dwes24.modelo.Planta;
import com.alejandromg.tarea3dwes24.servicios.Controlador;
import com.alejandromg.tarea3dwes24.servicios.PerfilUsuario;
import com.alejandromg.tarea3dwes24.servicios.ServiciosEjemplar;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;

@Controller
public class PlantasController {
	
	@Autowired
	private ServiciosPlanta servPlanta;
	
	@Autowired
	private ServiciosEjemplar servEjemplar;
	
	@Autowired
	private Controlador controlador;
	
	
	
	@GetMapping("/listado_plantas")
	public String listarPlantas(Model model) {
	    List<Planta> plantas = servPlanta.verTodas();
	    Map<String, Long> ejemplaresPorPlanta = new HashMap<>();
	    for (Planta planta : plantas) {
	        long cantidadEjemplares = servEjemplar.ejemplaresPorPlanta(planta.getCodigo());
	        ejemplaresPorPlanta.put(planta.getCodigo(), cantidadEjemplares);
	    }
	    model.addAttribute("plantas", plantas);
	    model.addAttribute("ejemplaresPorPlanta", ejemplaresPorPlanta);
	    return "listado_plantas";
	}
	
	
	@GetMapping("/gestion_plantas")
	public String gestionPlantas() {
	    return "gestion_plantas";
	}
	
	@GetMapping("/menu_modificar_plantas")
	public String modificarPlanta() {
		if (controlador.getPerfil() == null || controlador.getPerfil() != PerfilUsuario.ADMIN) {
    	    return "/acceso_perfiles";
    	}
	    return "menu_modificar_plantas";
	}

	    @GetMapping("/insertar_planta")
	    public String mostrarFormularioInsertarPlanta() {
	        return "insertar_planta";
	    }

	    @PostMapping("/insertar_planta")
	    public String insertarPlanta(@RequestParam("codigo") String codigo,@RequestParam("nombreComun") String nombreComun,@RequestParam("nombreCientifico") String nombreCientifico,Model model) {
	    	if (controlador.getPerfil() == null || controlador.getPerfil() != PerfilUsuario.ADMIN) {
	    	    return "/acceso_perfiles";
	    	}
	    	try {
	            Planta p = new Planta(codigo, nombreComun, nombreCientifico);
	            if(servPlanta.validarPlanta(p)) {
	            	model.addAttribute("mensaje", "Planta insertada");
	            	 servPlanta.insertar(p);
	            }else {
	            	model.addAttribute("error", "Error al insertar la planta");
	            }
	            
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al insertar la planta");
	        }
	        return "/insertar_planta";
	    }
	    
	    @PostMapping("/modificar_nombre_comun")
	    public String modificarNombreComun(@RequestParam("codigo") String codigo, @RequestParam("nombreComun") String nuevoNombreComun,Model model) {
	        try {
	        	if(servPlanta.validarCodigo(codigo)) {
	        		model.addAttribute("mensaje", "Nombre común modificado");
	        		servPlanta.actualizarNombreComun(codigo, nuevoNombreComun);
	        	}
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al modificar el nombre común");
	        }
	        model.addAttribute("plantas", servPlanta.verTodas());
	        return "modificar_nombreComun_planta";
	    }


	    @PostMapping("/modificar_nombre_cientifico")
	    public String modificarNombreCientifico(@RequestParam("codigo") String codigo, @RequestParam("nombreCientifico") String nuevoNombreCientifico, Model model) {
	        try {
	        	if(servPlanta.validarCodigo(codigo)) {
	        		model.addAttribute("mensaje", "Nombre científico modificado");
	        		servPlanta.actualizarNombreCientifico(codigo, nuevoNombreCientifico);
	        	}
	            
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al modificar el nombre científico");
	        }
	        model.addAttribute("plantas", servPlanta.verTodas());
	        return "modificar_nombreCientifico_planta";
	    }
	    
	    @GetMapping("/modificar_nombre_comun")
	    public String mostrarFormularioModificarNombreComun(Model model) {
	        model.addAttribute("plantas", servPlanta.verTodas());
	        return "modificar_nombreComun_planta";
	    }

	    @GetMapping("/modificar_nombre_cientifico")
	    public String mostrarFormularioModificarNombreCientifico(Model model) {
	        model.addAttribute("plantas", servPlanta.verTodas());
	        return "modificar_nombreCientifico_planta";
	    }

	    
	    @GetMapping("/invitado")
	    public String mostrarPantallaInvitado(Model model) {
	        model.addAttribute("plantas", servPlanta.verTodas()); 
	        return "invitado"; 
	    }
}
