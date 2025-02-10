package com.alejandromg.tarea3dwes24.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.alejandromg.tarea3dwes24.modelo.Mensaje;
import com.alejandromg.tarea3dwes24.repositorios.MensajeRepository;

@Service
public class ServiciosMensaje {

    @Autowired
    private MensajeRepository mensajeRepo;

    /**
     * Método para insertar un nuevo mensaje.
     * 
     * @param m Mensaje a insertar
     */
    
    public void insertar(Mensaje m) {
        mensajeRepo.saveAndFlush(m);
    }

    /**
     * Método para ver los mensajes dentro de un rango de fechas concreto
     * 
     * @param primeraFecha la primera fecha
     * @param segundaFecha la segunda fecha
     * @return un ArrayList de mensajes
     */
    public ArrayList<Mensaje> verMensajesRangoFechas(LocalDateTime primeraFecha, LocalDateTime segundaFecha) {
        List<Mensaje> mensajes = mensajeRepo.findMensajesBetweenFechas(primeraFecha, segundaFecha);
        return new ArrayList<>(mensajes);
    }
    
    /**
     * Método para ver todos los mensajes de la base de datos
     * 
     * @return una colección de mensajes
     */
    public Page<Mensaje> verMensajesPaginados(Pageable pageable) {
        return mensajeRepo.findAll(pageable);
    }

    /**
     * Método para ver los mensajes de una persona concreta
     * 
     * @param idPersona el id de esa persona
     * @return un ArrayList de mensajes
     */
    public ArrayList<Mensaje> verMensajesPorPersona(long idPersona) {
        List<Mensaje> mensajes = mensajeRepo.findMensajesByPersonaId(idPersona);
        return new ArrayList<>(mensajes);
    }
    
    /**
     * Método para ver los mensajes según el código concreto de una planta
     * 
     * @param codigoPlanta El código de la planta
     * @return un ArrayList de mensajes
     */
    public ArrayList<Mensaje> verMensajesPorCodigoPlanta(String codigoPlanta) {
        List<Mensaje> mensajes = mensajeRepo.findMensajesByCodigoPlanta(codigoPlanta);
        return new ArrayList<>(mensajes);
    }

    /**
     * Método para ver los mensajes según el ejemplar
     * 
     * @param idEjemplar un id de un ejemplar
     * @return un ArrayList de mensajes
     */
    public ArrayList<Mensaje> verMensajesPorEjemplar(long idEjemplar) {
        List<Mensaje> mensajes = mensajeRepo.findMensajesByEjemplarId(idEjemplar);
        return new ArrayList<>(mensajes);
    }

    /**
     * Método para hacer las validaciones de un mensaje
     * 
     * @param mensaje Un mensaje
     * @return si no se valida, false, si se valida, true
     */
    public boolean validarMensaje(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return false;
        }
        if (mensaje.length() > 500) {
            return false;
        }
        return true;
    }
    
    public Long contarMensajesPorPersona(Long idPersona) {
        return mensajeRepo.contarMensajesPorPersona(idPersona);
    }
}
