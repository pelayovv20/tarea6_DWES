package com.alejandromg.tarea3dwes24.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alejandromg.tarea3dwes24.modelo.Planta;

import jakarta.transaction.Transactional;

@Repository
public interface PlantaRepository extends JpaRepository<Planta, Long> {
	Optional<Planta> findById(Long id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Planta p SET p.nombreComun = :nombreComun WHERE p.codigo = :codigo")
	int actualizarNombreComun(@Param("codigo") String codigo, @Param("nombreComun") String nombreComun);

	@Transactional
	@Modifying
	@Query("UPDATE Planta p SET p.nombreCientifico = :nombreCientifico WHERE p.codigo = :codigo")
	int actualizarNombreCientifico(@Param("codigo") String codigo, @Param("nombreCientifico") String nombreCientifico);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Planta p WHERE p.codigo = :codigo")
	int borrarPlantaPorCodigo(@Param("codigo") String codigo);


	Optional<Planta> findByCodigo(String codigo);

	boolean existsByCodigo(String codigo);

	List<Planta> findAllByOrderByNombreComunAsc();
}
