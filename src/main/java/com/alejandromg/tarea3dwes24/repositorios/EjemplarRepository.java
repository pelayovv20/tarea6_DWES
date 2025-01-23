package com.alejandromg.tarea3dwes24.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alejandromg.tarea3dwes24.modelo.Ejemplar;

import jakarta.transaction.Transactional;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {

	@Query("SELECT COUNT(e) FROM Ejemplar e")
	long contarEjemplares();

	@Query("SELECT e FROM Ejemplar e WHERE e.planta.codigo = :codigoPlanta")
	List<Ejemplar> ejemplaresPorTipoPlanta(@Param("codigoPlanta") String codigoPlanta);

	@Transactional
	@Modifying
	@Query("UPDATE Ejemplar e SET e.nombre = :nuevoNombre WHERE e.id = :idEjemplar")
	int cambiarNombre(@Param("idEjemplar") Long idEjemplar, @Param("nuevoNombre") String nuevoNombre);

}
