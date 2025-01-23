package com.alejandromg.tarea3dwes24.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandromg.tarea3dwes24.modelo.Credenciales;
import com.alejandromg.tarea3dwes24.modelo.Persona;
import com.alejandromg.tarea3dwes24.repositorios.CredencialesRepository;
import com.alejandromg.tarea3dwes24.repositorios.PersonaRepository;

@Service
public class ServiciosCredenciales {
	@Autowired
	CredencialesRepository credencialesRepo;
	
	@Autowired
	PersonaRepository personaRepo;
	
	/**
     * Método para autenticar un usuario dado el nombre de usuario y la contraseña.
     *
     * @param usuario Nombre del usuario
     * @param password Contraseña del usuario
     * @return true si las credenciales son correctas, false en caso contrario.
     */
	 public boolean autenticar(String usuario, String password) {
	    return credencialesRepo.existsByUsuarioAndPassword(usuario, password);
	 }

    /**
     * Método para comprobar si el usuario ya existe.
     *
     * @param usuario Nombre del usuario a verificar.
     * @return true si el usuario existe, false en caso contrario.
     */
    public boolean usuarioExistente(String usuario) {
        return credencialesRepo.existsByUsuario(usuario);
    }

    /**
     * Método para insertar un nuevo registro de credenciales.
     *
     * @param Nombre del usuario.
     * @param Contraseña del usuario.
     * @param id de la persona asociada.
     * 
     */
    public void insertar(String usuario, String password, Long idPersona) {
        Persona p = personaRepo.findById(idPersona).orElse(null);
        Credenciales credenciales = new Credenciales();
        credenciales.setUsuario(usuario);
        credenciales.setPassword(password);
        credenciales.setPersona(p);
        credencialesRepo.saveAndFlush(credenciales);
    }

    /**
     * Método para validar la contraseña según el formato.
     *
     * @param contraseña Contraseña a validar.
     * @return true si la contraseña es válida, false en caso contrario.
     */
    public boolean validarContraseña(String contraseña) {
        return contraseña.matches("^(?=.*[.,])[A-Za-z0-9.,]{8,}$");
    }
	public Persona buscarPersonaPorUsuario(String usuario) {
		return credencialesRepo.findPersonaByUsuario(usuario);
	}
	
	public int cambiarContraseña (String usuario, String contraseña) {
		return credencialesRepo.cambiarContraseña(usuario, contraseña);
	}
}