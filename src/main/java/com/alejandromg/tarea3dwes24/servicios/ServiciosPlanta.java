package com.alejandromg.tarea3dwes24.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.alejandromg.tarea3dwes24.modelo.Planta;
import com.alejandromg.tarea3dwes24.repositorios.PlantaRepository;

import jakarta.transaction.Transactional;

@Service
public class ServiciosPlanta {

    @Autowired
    private PlantaRepository plantaRepo;

    /**
     * Método para insertar una nueva planta.
     * 
     * @param p Planta a insertar
     * @return El ID de la planta si se ha insertado correctamente
     */
    public void insertar(Planta p) {
    	plantaRepo.saveAndFlush(p);
    }

    /**
     * Método para obtener todas las plantas.
     * 
     * @return Colección de todas las plantas
     */
    public List<Planta> verTodas() {
    	return plantaRepo.findAllByOrderByNombreComunAsc();
    }

    /**
     * Método para buscar una planta por ID.
     * 
     * @param id ID de la planta
     * @return Planta encontrada o null si no se ha encontrado
     */
    public Planta buscarPorID(long id) {
        Optional<Planta> plantas = plantaRepo.findById(id);
        return plantas.orElse(null);
    }

    /**
     * Método para actualizar el nombre común de una planta.
     * 
     * @param codigo Código de la planta
     * @param nombreComun Nuevo nombre común
     * @return true si se ha actualizado correctamente, false si no
     */
    @Transactional
    public boolean actualizarNombreComun(String codigo, String nombreComun) { 
        Optional<Planta> plantas = plantaRepo.findByCodigo(codigo);
        if (plantas.isPresent()) {
            Planta planta = plantas.get();
            planta.setNombreComun(nombreComun);
            plantaRepo.saveAndFlush(planta);
            return true;
        }
        return false;
    }

    /**
     * Método para actualizar el nombre científico de una planta.
     * 
     * @param codigo Código de la planta
     * @param nombreCientifico Nuevo nombre científico
     * @return true si se ha actualizado correctamente, false si no
     */
    @Transactional
    public boolean actualizarNombreCientifico(String codigo, String nombreCientifico) {
        Optional<Planta> plantas = plantaRepo.findByCodigo(codigo);
        if (plantas.isPresent()) {
            Planta p = plantas.get();
            p.setNombreCientifico(nombreCientifico);
            plantaRepo.saveAndFlush(p);
            return true;
        }
        return false;
    }

    /**
     * Método para comprobar si existe el código de una planta.
     * 
     * @param codigo Código de la planta
     * @return true si el código ya existe, false si no
     */
    public boolean codigoExistente(String codigo) {
        return plantaRepo.existsByCodigo(codigo);
    }

    /**
     * Método para validar una planta.
     * 
     * @param p Planta a validar
     * @return true si la planta es válida, false si no
     */
    public boolean validarPlanta(Planta p) {
        if (p.getCodigo() == null || p.getCodigo().isEmpty()) {
            return false;
        }
        if (p.getCodigo().length() < 3 || p.getCodigo().length() > 50) {
            return false;
        }
        if (p.getNombreCientifico() == null || p.getNombreComun() == null) {
            return false;
        }
        if (p.getNombreCientifico().isEmpty() || p.getNombreComun().isEmpty()) {
            return false;
        }
        if (!p.getCodigo().matches("^[A-ZÁÉÍÓÚÑ]+$")) {
            return false;
        }
        if (p.getNombreCientifico().length() < 3 || p.getNombreCientifico().length() > 100) {
            return false;
        }
        if (p.getNombreComun().length() < 3 || p.getNombreComun().length() > 100) {
            return false;
        }
        if (!p.getNombreCientifico().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")
                || !p.getNombreComun().matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            return false;
        }
        return true;
    }

    /**
     * Método para validar el código de una planta.
     * 
     * @param codigo Código a validar
     * @return true si el código es válido, false si no
     */
    public boolean validarCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            return false;
        }
        if (!codigo.matches("^[A-Za-z0-9]+$")) {
            return false;
        }
        if (codigo.length() < 3 || codigo.length() > 50) {
            return false;
        }
        return true;
    }
    
    /**
     * Método para buscar una planta por su código.
     * 
     * @param codigo a validar
     * @return si existe o no esa planta con ese código
     */
    public Planta buscarPorCodigo(String codigo) {
        Optional<Planta> plantaOptional = plantaRepo.findByCodigo(codigo);
        return plantaOptional.orElse(null);
    }
    
    /**
     * Método para borrar una planta de la base de datos
     * @param codigo El codigo de la planta que se quiere eliminar
     * @return false si no se ha eliminado, true si sí.
     */
    @Transactional
    @Modifying
    public boolean borrarPlanta(String codigo) {
        try {
            if (plantaRepo.existsByCodigo(codigo)) {
                plantaRepo.borrarPlantaPorCodigo(codigo);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar la planta: " + e.getMessage());
            return false;
        }
    }
}

