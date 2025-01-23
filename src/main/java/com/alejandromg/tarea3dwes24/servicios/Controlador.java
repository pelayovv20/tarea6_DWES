package com.alejandromg.tarea3dwes24.servicios;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;


//Esta clase Controlador se encarga del manejo de la sesión y de los usuarios autenticados en el programa

@Service
public class Controlador {
	private Long idUsuario;
    private String usuarioAutenticado;
    private PerfilUsuario perfil; //Ahora también se guarda el perfil del usuario, de tipo enum (PERSONAL O ADMIN)
    private LocalDateTime fechaInicioSesion;

    public void setUsuarioAutenticado(String usuario) {
        this.usuarioAutenticado = usuario;
    }

    public String getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
    
    public PerfilUsuario getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilUsuario perfil) {
		this.perfil = perfil;
	}
	
	public LocalDateTime getFechaInicioSesion() {
		return fechaInicioSesion;
	}

	public void setFechaInicioSesion(LocalDateTime fechaInicioSesion) {
		this.fechaInicioSesion = fechaInicioSesion;
	}
	
	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	//Método para iniciar la sesión de un usuario cuando se loguea
    public void iniciarSesion(Long idUsuario, String nombreUsuario, PerfilUsuario perfilUsuario, LocalDateTime fechaInicioSesion) {
        this.idUsuario = idUsuario;
        this.usuarioAutenticado = nombreUsuario;
        this.perfil = perfilUsuario;
        this.fechaInicioSesion = fechaInicioSesion;
    }

	/**
	 * Este método sirve para cerrar sesión, ya que pone todas las variables a nulo.
	 * El perfil de usuario también lo pone a nulo
	 * Esta opción la añado en el menú para que el usuario pueda cerrar su sesión
	 */
	public void cerrarSesion() {
		this.idUsuario = null;
		this.usuarioAutenticado = null;
		this.perfil = null;
		this.fechaInicioSesion = null;
	}
}
