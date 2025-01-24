package com.alejandromg.tarea3dwes24.vista;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.alejandromg.tarea3dwes24.modelo.Ejemplar;
import com.alejandromg.tarea3dwes24.modelo.Mensaje;
import com.alejandromg.tarea3dwes24.modelo.Persona;
import com.alejandromg.tarea3dwes24.modelo.Planta;
import com.alejandromg.tarea3dwes24.servicios.Controlador;
import com.alejandromg.tarea3dwes24.servicios.ServiciosEjemplar;
import com.alejandromg.tarea3dwes24.servicios.ServiciosMensaje;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPersona;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;
/*
 * Utilizo las anotaciones @Lazy para que las inyecciones de las fachadas 
 * solo se carguen cuando haga falta, y así Spring pueda manejarlas bien
 */
@Component
public class FachadaPersonal {

    @Autowired
    @Lazy
    private Controlador controlador;

    @Autowired
    private ServiciosEjemplar serviciosEjemplar;

    @Autowired
    private ServiciosMensaje serviciosMensaje;

    @Autowired
    private ServiciosPersona serviciosPersona;

    @Autowired
    private ServiciosPlanta serviciosPlanta;
    

    @Autowired
    @Lazy
    private FachadaAdmin fachadaAdmin;

    @Autowired
    @Lazy
    private FachadaInvitado fachadaInvitado;

    private Scanner in = new Scanner(System.in);
    public void menuPersonal() {
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t\t------MENÚ DEL PERSONAL------");
            System.out.println("\t\t\t\t\tSelecciona una opción:");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            System.out.println("\t\t\t\t\t1. VER TODAS LAS PLANTAS.");
            System.out.println("\t\t\t\t\t2. Gestión de ejemplares.");
            System.out.println("\t\t\t\t\t3. Gestión de mensajes.");
            System.out.println("\t\t\t\t\t4. CERRAR SESIÓN.");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 4) {
                    System.out.println("Opción incorrecta.");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        fachadaInvitado.verTodasPlantas();
                        break;
                    case 2:
                        menuPersonalEjemplares();
                        break;
                    case 3:
                        menuPersonalMensajes();
                        break;
                    case 4:
                        controlador.cerrarSesion();
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número.");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 4);
    }

    public void menuPersonalEjemplares() {
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t\tSelecciona una opción:");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            System.out.println("\t\t\t\t\t1. Registrar nuevo ejemplar.");
            System.out.println("\t\t\t\t\t2. Filtrar ejemplares por tipo de planta.");
            System.out.println("\t\t\t\t\t3. Ver mensajes de un ejemplar.");
            System.out.println("\t\t\t\t\t4. Borrar un ejemplar.");
            System.out.println("\t\t\t\t\t5. Volver al menú principal.");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción incorrecta.");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        fachadaAdmin.nuevoEjemplar();
                        break;
                    case 2:
                        filtrarEjemplaresPorCodigoPlanta();
                        break;
                    case 3:
                        fachadaAdmin.verMensajesEjemplar();
                        break;
                    case 4:
                    	borrarEjemplar();
                    	break;
                    case 5:
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número.");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 4);
    }

    public void menuPersonalMensajes() {
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t\tSelecciona una opción:");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            System.out.println("\t\t\t\t\t1. Nuevo mensaje.");
            System.out.println("\t\t\t\t\t2. Ver todos los mensajes.");
            System.out.println("\t\t\t\t\t3. Ver mensajes por persona.");
            System.out.println("\t\t\t\t\t4. Ver mensajes por rango de fechas.");
            System.out.println("\t\t\t\t\t5. Ver mensajes por tipo de planta.");
            System.out.println("\t\t\t\t\t6. Volver al menú principal.");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 6) {
                    System.out.println("Opción incorrecta.");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        nuevoMensaje();
                        break;
                    case 2:
                        fachadaAdmin.verTodosMensajes();
                        break;
                    case 3:
                        verMensajesPersona();
                        break;
                    case 4:
                        verMensajesFechas();
                        break;
                    case 5:
                        verMensajesTipoPlanta();
                        break;
                    case 6:
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número.");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 6);
    }
    

    /**
	 * Método para ver listar todos los ejemplares por pantalla
	 * 
	 */
    public void verTodosEjemplares() {
        ArrayList<Ejemplar> ejemplares = (ArrayList<Ejemplar>) serviciosEjemplar.verTodos(); //Cargo todos los ejemplares en un ArrayList para mostrarlos por pantalla
        if (ejemplares == null || ejemplares.isEmpty()) {
            System.out.println("Lo siento, no hay ejemplares para mostrar en la base de datos.");
            return;
        }
        System.out.println();
        System.out.println("Todos los ejemplares: ");
        for (Ejemplar e : ejemplares) {
            System.out.println(e);
            System.out.println();
        }
    }
    
    /**
	 * Método para crear un nuevo mensaje, con sus validaciones y controlando las
	 * excepciones que pueden surgir
	 * 
	 */
    public void nuevoMensaje() {
        boolean correcto = false;
        do {
            try {
            	verTodosEjemplares();
                System.out.println();
                System.out.print("Introduce el id del ejemplar para ponerle un mensaje: "); //Le pido el id
                int idEjemplar = in.nextInt();
                in.nextLine();
                Ejemplar ejemplar = serviciosEjemplar.buscarPorID((long) idEjemplar);
                if (ejemplar == null) {
                    System.out.println("No existe un ejemplar con el ID proporcionado");
                } else {
                    System.out.println("Introduce el mensaje: ");
                    String mensajeTexto = in.nextLine().trim();
                    if (mensajeTexto.isEmpty()) {
                        System.out.println("El mensaje no puede estar vacío.");
                    } else if (serviciosMensaje.validarMensaje(mensajeTexto)) {
                        String usuarioAutenticado = controlador.getUsuarioAutenticado();
                        Persona p = serviciosPersona.buscarPorNombre(usuarioAutenticado); //Al campo persona del mensaje, se le añade el usuarioAutenticado
                        if (p == null) {
                            System.out.println("Error: No se ha encontrado la persona autenticada");
                        } else {
                            Mensaje nuevoMensaje = new Mensaje(LocalDateTime.now(), mensajeTexto, p, ejemplar); //Inserto el mensaje con todos los campos
                            serviciosMensaje.insertar(nuevoMensaje);
                            System.out.println("Mensaje añadido.");
                            correcto = true;
                        }
                    } else {
                        System.out.println("El mensaje no es válido, posiblemente sea demasiado largo");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes introducir un número válido");
                in.nextLine();
            }
        } while (!correcto);
    }


    /**
	 * Método para filtrar los ejemplares
	 * según el código de la planta
	 * 
	 */
    public void filtrarEjemplaresPorCodigoPlanta() {
    	in.nextLine();
    	 ArrayList<Planta> plantas = (ArrayList<Planta>) serviciosPlanta.verTodas(); //Cargo en un ArrayList todas las plantas de la base de datos para mostrarlas una a una
         if (plantas == null || plantas.isEmpty()) {
             System.out.println("Lo siento, no hay plantas para mostrar en la base de datos");
         }
         System.out.println("Todas las plantas: ");
         System.out.println();
         for (Planta p : plantas) {
             System.out.println(p);
             System.out.println();
         }
        try {
            System.out.print("Introduce el código de la planta para ver los ejemplares: ");
            String codigo = in.nextLine().trim().toUpperCase();
            boolean existe = serviciosPlanta.codigoExistente(codigo);
            if (existe) {
                ArrayList<Ejemplar> ejemplares = serviciosEjemplar.ejemplaresPorTipoPlanta(codigo); //Cargo en un ArrayList todos los ejemplares con ese código de planta 
                if (ejemplares.isEmpty()) {
                    System.out.println("No hay ejemplares para la planta con código: " + codigo);
                } else {
                    System.out.println("Ejemplares con el código " + codigo + ":");
                    System.out.println();                   
                    for (Ejemplar e : ejemplares) {
                        System.out.println(e);
                        System.out.println();    
                    }
                }
            } else {
                System.out.println("No se encontró ninguna planta con el código especificado: " + codigo);
            }
        } catch (Exception e) {
            System.out.println("Error al intentar filtrar los ejemplares: " + e.getMessage());
        }
    }

    /**
	 * Método para listar todos los mensajes 
	 * de una persona concreta por pantalla
	 * 
	 */
    public void verMensajesPersona() {
    	ArrayList<Persona> personas = (ArrayList<Persona>) serviciosPersona.verTodos(); //Cargo en un ArrayList todas las personas de la base de datos para mostrarlas una a una
        if (personas == null || personas.isEmpty()) {
            System.out.println("Lo siento, no hay personas para mostrar en la base de datos");
        }
        System.out.println("Todas las personas: ");
        System.out.println();
        for (Persona pers : personas) {
            System.out.println(pers);
            System.out.println();
        }
        System.out.print("Introduce el id de una persona para ver sus mensajes: ");
        try {
            long idPersona = in.nextLong();
            ArrayList<Mensaje> mensajes = serviciosMensaje.verMensajesPorPersona(idPersona); //Cargo en un ArrayList todos los mensajes de una persona para listarlos por pantalla
            if (mensajes.isEmpty()) {
                System.out.println("No se encontraron mensajes para la persona: " + idPersona);
            } else {
                System.out.println("Mensajes:");
                System.out.println();
                for (Mensaje m : mensajes) {
                    System.out.println(m);
                    System.out.println();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Debes introducir un número válido.");
            in.nextLine();
        } catch (Exception e) {
            System.out.println("Se produjo un error al intentar obtener los mensajes: " + e.getMessage());
        }
    }

    /**
	 * Método para listar los mensajes de un tipo de planta concreto
	 * 
	 */
    public void verMensajesTipoPlanta() {
    	in.nextLine();
    	 ArrayList<Planta> plantas = (ArrayList<Planta>) serviciosPlanta.verTodas(); //Cargo en un ArrayList todas las plantas de la base de datos para mostrarlas una a una
         if (plantas == null || plantas.isEmpty()) {
             System.out.println("Lo siento, no hay plantas para mostrar en la base de datos");
         }
         System.out.println("Todas las plantas: ");
         System.out.println();
         for (Planta p : plantas) {
             System.out.println(p);
             System.out.println();
         }
        System.out.print("Introduce el código de una planta: ");
        String codigo = in.nextLine().trim().toUpperCase();
        try {
            ArrayList<Mensaje> mensajes = serviciosMensaje.verMensajesPorCodigoPlanta(codigo); //Cargo en un ArrayList todos los mensajes de ese código concreto
            if (mensajes.isEmpty()) {
                System.out.println("No se encontraron mensajes para la planta con código: " + codigo);
            } else {
                System.out.println("Mensajes para la planta con el código " + codigo + ":");
                System.out.println();
                for (Mensaje m : mensajes) {
                    System.out.println(m);
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("No se encontraron mensajes para esa planta");
        }
    }

    /**
	 * Método para listar los mensajes 
	 * dentro de un rango concreto de fechas introducido por el usuario
	 * 
	 */
    public void verMensajesFechas() {
    	in.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime fechaInicio = null;
        LocalDateTime fechaFin = null;
      //Pido ambas fechas
        do {
            try {
                System.out.print("Introduce la primera fecha y la hora con el formato: dd-MM-yyyy HH:mm"
                		+ "");
                String fechaInicioIntro = in.nextLine();
                fechaInicio = LocalDateTime.parse(fechaInicioIntro, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido.");
            }
        } while (fechaInicio == null);
        do {
            try {
                System.out.print("Introduce la segunda fecha y la hora con el formato: dd-MM-yyyy HH:mm ");
                String fechaFinIntro = in.nextLine();
                fechaFin = LocalDateTime.parse(fechaFinIntro, formatter);
                if (fechaFin.isBefore(fechaInicio)) {
                    System.out.println("La fecha de fin no puede ser anterior a la fecha de inicio.");
                    fechaFin = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido.");
            }
        } while (fechaFin == null);
        ArrayList<Mensaje> mensajes = serviciosMensaje.verMensajesRangoFechas(fechaInicio, fechaFin); //Cargo a un ArrayList todos los mensajes que hay entre esas dos fechas y los listo por pantalla
        if (mensajes.isEmpty()) {
            System.out.println("No se encontraron mensajes en el rango de fechas proporcionado.");
        } else {
            System.out.println("Mensajes encontrados:");
            System.out.println();
            for (Mensaje m : mensajes) {
                System.out.println(m);
                System.out.println();
            }
        }
    }
    
    /**
	 * Método para borrar un ejemplar de la base de datos
	 * 
	 * Este método no se pedía, pero he decidirlo incluirlo como mejora, ya que así resulta más fácil para manipular el programa,
	 * el usuario administrador y los perfiles de personal pueden eliminar ejemplares de la base de datos
	 * 
	 */
    public void borrarEjemplar() {
    	verTodosEjemplares();
    	System.out.println();
        System.out.print("Introduce el id del ejemplar que quieres borrar: ");
        try {
            Long idEjemplar = in.nextLong();
            in.nextLine();
            boolean eliminado = serviciosEjemplar.borrarEjemplar(idEjemplar);
            if (eliminado) {
                System.out.println("El ejemplar ha sido borrado");
            } else {
                System.out.println("No se encontró un ejemplar con ese id");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes introducir un número válido");
            in.nextLine();
        } catch (Exception e) {
            System.out.println("Error durante el borrado del ejemplar: " + e.getMessage());
        }
    }
    
}
