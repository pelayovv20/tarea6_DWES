package com.alejandromg.tarea3dwes24.servicios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandromg.tarea3dwes24.modelo.Ejemplar;
import com.alejandromg.tarea3dwes24.repositorios.EjemplarRepository;

@Service
public class ServiciosEjemplar {

    @Autowired
    private EjemplarRepository ejemplarRepo;
    
    /**
     * Método para insertar un nuevo ejemplar
     * 
     * @param e Ejemplar a insertar
     */
    public void insertar(Ejemplar e) {
    	ejemplarRepo.saveAndFlush(e);
    }
    
    /**
     * Método para insertar una nueva planta.
     * 
     * @return Una colección de ejemplares
     */
    public Collection<Ejemplar> verTodos() {
        return ejemplarRepo.findAll();
    }
    
    
    /**
     * Método para buscar por id un ejemplar
     * 
     * @param id Un id de un ejemplar
     * @return Un objeto de tipo ejemplar
     */
    public Ejemplar buscarPorID(long id) {
        Optional<Ejemplar> ejemplares = ejemplarRepo.findById(id);
        return ejemplares.orElse(null);
    }
    
    /**
     * Método para cambiar el nombre del ejemplar para que se inserte
     * con el formato correcto en la base de datos
     * 
     * @param idEjemplar El id del ejemplar
     * @param nuevoNombre El nuevo nombre del ejemplar
     * @return si se ha modificado correctamente, número mayor que 0
     */
    @Transactional
    public boolean cambiarNombre(long idEjemplar, String nuevoNombre) {
        int filas = ejemplarRepo.cambiarNombre(idEjemplar, nuevoNombre);
        return filas > 0;
    }

    /**
     * Método para contar ejemplares
     * 
     * @return el número de ejemplares que se han encontrado
     */
    public long contarEjemplares() {
        return ejemplarRepo.contarEjemplares();
    }

    /**
     * Método para recuperar de la base de datos todos los ejemplares
     * de un tipo de planta concreto
     * 
     * @param codigo El código de la planta
     * @return una colección de ejemplares
     */
    public ArrayList<Ejemplar> ejemplaresPorTipoPlanta(String codigo) {
    	ArrayList<Ejemplar> ejemplares = (ArrayList<Ejemplar>) ejemplarRepo.ejemplaresPorTipoPlanta(codigo);
        return (ArrayList<Ejemplar>) ejemplares;
    }

    /**
     * Método para hacer las validaciones del ejemplar
     * @param Un objeto de tipo ejemplar
     * @return false si no se valida, true si se valida correctamente
     */
    public boolean validarEjemplar(Ejemplar e) {
        if (e.getPlanta() == null || e.getPlanta().getCodigo() == null || e.getPlanta().getCodigo().isEmpty()) {
            return false;
        }
        if (e.getPlanta().getCodigo().length() < 3 || e.getPlanta().getCodigo().length() > 20) {
            return false;
        }
        if (e.getNombre() == null || e.getNombre().isEmpty() || e.getNombre().length() < 3) {
            return false;
        }
        return true;
    }
    
    /**
     * Método para borrar un ejemplar
     * @param id El id del ejemplar que se quiere borrar
     * @return false si no se ha borrado, true si se ha borrado correctamente
     */
    @Transactional
    @Modifying
    public boolean borrarEjemplar(Long id) {
        try {
            if (ejemplarRepo.existsById(id)) {
                ejemplarRepo.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el ejemplar: " + e.getMessage());
            return false;
        }
    }
}


