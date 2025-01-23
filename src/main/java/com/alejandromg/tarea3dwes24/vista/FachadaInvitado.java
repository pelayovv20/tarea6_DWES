package com.alejandromg.tarea3dwes24.vista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.alejandromg.tarea3dwes24.modelo.Planta;
import com.alejandromg.tarea3dwes24.servicios.Controlador;
import com.alejandromg.tarea3dwes24.servicios.PerfilUsuario;
import com.alejandromg.tarea3dwes24.servicios.ServiciosCredenciales;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPersona;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;

/*
 * Utilizo las anotaciones @Lazy para que las inyecciones de las fachadas 
 * solo se carguen cuando haga falta, y así Spring pueda manejarlas bien
 */
@Component
public class FachadaInvitado {

	@Autowired
	private ServiciosPersona servPersona;
    @Autowired
    private ServiciosCredenciales servCred;

    @Autowired
    private ServiciosPlanta servPlanta;

    @Autowired
    @Lazy
    private FachadaAdmin fachadaAdmin;

    @Autowired
    @Lazy
    private FachadaPersonal fachadaPersonal;

    @Autowired
    @Lazy
    private Controlador controlador; 

    private Scanner in = new Scanner(System.in);

    public void menuInvitado() {
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t\t------GESTIÓN DEL VIVERO------");
            System.out.println();
            System.out.println("\t\t\t\t\tSelecciona una opción: ");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            System.out.println("\t\t\t\t\t1. VER TODAS LAS PLANTAS");
            System.out.println("\t\t\t\t\t2. LOGUEARSE");
            System.out.println("\t\t\t\t\t3. SALIR DEL PROGRAMA");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            try {
                opcion = in.nextInt();
                switch (opcion) {
                case 1:
                    verTodasPlantas();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Saliendo del programa");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción incorrecta");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número válido.");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 3);
    }

    /**
     * Método para hacer login desde el menú de invitado, 
     * pudiendo iniciar sesión como administrador o como un usuario de personal
     */
    public void login() {
	    in.nextLine(); 
	    System.out.print("Introduce usuario: ");
	    String usuario = in.nextLine().trim();
	    System.out.print("Introduce contraseña: ");
	    String contraseña = in.nextLine().trim();
	    try {
	        boolean autenticar = servCred.autenticar(usuario, contraseña);
	        if (autenticar) {
	            System.out.println("Has iniciado sesión como: " + usuario);
	            long id = servPersona.IdUsuarioAutenticado(usuario);
	            PerfilUsuario perfil;
	            if ("admin".equalsIgnoreCase(usuario)) {
	                perfil = PerfilUsuario.ADMIN;
	            } else {
	                perfil = PerfilUsuario.PERSONAL;
	            }
	            controlador.iniciarSesion(id, usuario, perfil, LocalDateTime.now());
	            if (perfil == PerfilUsuario.ADMIN) {
	                System.out.println("Eres el usuario administrador");
	                fachadaAdmin.menuAdmin();
	            } else {
	                System.out.println("Eres un usuario del personal del vivero");
	                fachadaPersonal.menuPersonal();
	            }
	        } else {
	            System.out.println("Usuario o contraseña incorrectos");
	        }
	    } catch (Exception e) {
	        System.out.println("No se ha podido iniciar sesión: " + e.getMessage());
	    }
	}


    
/**
 * Método para listar todas las plantas de la base de datos.
 * Las muestra por pantalla en orden alfabético
 */
    public void verTodasPlantas() {
        ArrayList<Planta> plantas = (ArrayList<Planta>) servPlanta.verTodas(); //Cargo en un ArrayList todas las plantas de la base de datos para mostrarlas una a una
        if (plantas == null || plantas.isEmpty()) {
            System.out.println("Lo siento, no hay plantas para mostrar en la base de datos");
            return;
        }
        System.out.println("Todas las plantas: ");
        System.out.println();
        for (Planta p : plantas) {
            System.out.println(p);
            System.out.println();
        }
    }
}