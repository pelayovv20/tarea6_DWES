package com.alejandromg.tarea3dwes24.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alejandromg.tarea3dwes24.modelo.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

	boolean existsByEmail(String email);

	@Query("SELECT c.persona.id FROM Credenciales c WHERE c.usuario = :usuario")
	Long idUsuarioAutenticado(@Param("usuario") String usuario);

	Persona findByNombre(String nombre);
	

}