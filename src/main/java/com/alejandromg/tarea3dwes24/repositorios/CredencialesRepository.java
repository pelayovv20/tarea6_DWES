package com.alejandromg.tarea3dwes24.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alejandromg.tarea3dwes24.modelo.Credenciales;
import com.alejandromg.tarea3dwes24.modelo.Persona;

import jakarta.transaction.Transactional;

@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {

	boolean existsByUsuario(String usuario);

	boolean existsByUsuarioAndPassword(String usuario, String password);

	@Query("SELECT c.persona FROM Credenciales c WHERE c.usuario = :usuario")
	Persona findPersonaByUsuario(@Param("usuario") String usuario);

	
	@Transactional
    @Modifying
    @Query("UPDATE Credenciales c SET c.password = :nuevaPassword WHERE c.usuario = :usuario")
    int cambiarContraseña(@Param("usuario") String usuario, @Param("nuevaPassword") String nuevaPassword);


}
