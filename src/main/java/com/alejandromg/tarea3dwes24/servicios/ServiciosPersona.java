package com.alejandromg.tarea3dwes24.servicios;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.alejandromg.tarea3dwes24.modelo.Persona;
import com.alejandromg.tarea3dwes24.repositorios.CredencialesRepository;
import com.alejandromg.tarea3dwes24.repositorios.PersonaRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiciosPersona {

    @Autowired
    private PersonaRepository personaRepo;
    @Autowired
    private CredencialesRepository credRepo;

    /**
     * Método para insertar una persona en la base de datos
     * 
     * @param pers Persona a insertar
     */
    public void insertar(Persona pers) {
        personaRepo.saveAndFlush(pers);
    }
    /**
     * Método para listar todas las personas de la base de datos
     * 
     * @return Una colección de personas
     */
    public Collection<Persona> verTodos() {
        return personaRepo.findAll();
    }

    /**
     * Método para validar si un email existe o no en la base de datos
     * @param email El email que se quiere comprobar
     * @return true si se ha validado, false si no
     */
    public boolean emailExistente(String email) {
        return personaRepo.existsByEmail(email);
    }

    /**
     * Método para saber cual es el usuario está autenticado
     * @param usuario Un usuario
     * @return
     */
    public long IdUsuarioAutenticado(String usuario) {
		Long idPersona = personaRepo.idUsuarioAutenticado(usuario);
        return (idPersona != null) ? idPersona : -1;
	}

    /**
	 * Método para las validaciones de una persona
	 * @param pers Una persona
	 * @return false si no se ha validado, true si se ha validado
	 */
    public boolean validarPersona(Persona pers) {
        if (pers == null) {
            return false;
        }
        if (pers.getNombre() == null || pers.getNombre().isEmpty()) { 
            return false;
        }
        if (pers.getNombre().length() < 3 || pers.getNombre().length() > 20) {
            return false;
        }
        if (pers.getEmail() == null || pers.getEmail().isEmpty()) {
            return false;
        }
        if (pers.getEmail().length() < 5 || !pers.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$") || pers.getEmail().length() > 40) {
            return false;
        }
		return true;
    }
    
    /**
     * Método para buscar una persona por nombre
     * @param email El nombre de la persona que se quiere validar
     * @return Un objeto de tipo persona
     */
    public Persona buscarPorNombre(String nombre){
    	return personaRepo.findByNombre(nombre);
    }
    
    /**
     * Método para borrar una persona de la base de datos
     * @param id El id de la persona que se quiere eliminar
     * @return false si no se ha eliminado, true si sí.
     */
    @Transactional
    @Modifying
    public boolean borrarPersona(Long id) {
        try {
            if (personaRepo.existsById(id)) {
                personaRepo.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la persona: " + e.getMessage());
            return false;
        }
    }
	public Optional<Persona> buscarPorId(long idUsuario) {
		return personaRepo.findById(idUsuario);
	}
	
	public Persona buscarPersonaPorUsuario(String usuario) {
	    Persona persona = credRepo.findPersonaByUsuario(usuario);
	    return persona;
	}
	
}
