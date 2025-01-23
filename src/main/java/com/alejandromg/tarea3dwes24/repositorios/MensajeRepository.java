package com.alejandromg.tarea3dwes24.repositorios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alejandromg.tarea3dwes24.modelo.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository <Mensaje, Long>{
	
    @Query("SELECT m FROM Mensaje m WHERE m.fechaHora BETWEEN :primeraFecha AND :segundaFecha")
    List<Mensaje> findMensajesBetweenFechas(@Param("primeraFecha") LocalDateTime primeraFecha,@Param("segundaFecha") LocalDateTime segundaFecha);

    @Query("SELECT m FROM Mensaje m WHERE m.persona.id = :idPersona")
    List<Mensaje>findMensajesByPersonaId(@Param("idPersona")long idPersona);

    @Query("SELECT m FROM Mensaje m INNER JOIN m.ejemplar e INNER JOIN e.planta p WHERE p.codigo = :codigoPlanta")
    List<Mensaje>findMensajesByCodigoPlanta(@Param("codigoPlanta") String codigoPlanta);

    @Query("SELECT m FROM Mensaje m WHERE m.ejemplar.id = :idEjemplar")
    List<Mensaje> findMensajesByEjemplarId(@Param("idEjemplar")long idEjemplar);
    
    @Query("SELECT COUNT(m) FROM Mensaje m WHERE m.persona.id = :idPersona")
    Long contarMensajesPorPersona(@Param("idPersona") Long idPersona);

}