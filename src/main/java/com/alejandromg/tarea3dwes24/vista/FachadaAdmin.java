package com.alejandromg.tarea3dwes24.vista;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.alejandromg.tarea3dwes24.modelo.Credenciales;
import com.alejandromg.tarea3dwes24.modelo.Ejemplar;
import com.alejandromg.tarea3dwes24.modelo.Mensaje;
import com.alejandromg.tarea3dwes24.modelo.Persona;
import com.alejandromg.tarea3dwes24.modelo.Planta;
import com.alejandromg.tarea3dwes24.servicios.Controlador;
import com.alejandromg.tarea3dwes24.servicios.ServiciosCredenciales;
import com.alejandromg.tarea3dwes24.servicios.ServiciosEjemplar;
import com.alejandromg.tarea3dwes24.servicios.ServiciosMensaje;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPersona;
import com.alejandromg.tarea3dwes24.servicios.ServiciosPlanta;
/*
 * Utilizo las anotaciones @Lazy para que las inyecciones de las fachadas 
 * solo se carguen cuando haga falta, y así Spring pueda manejarlas bien
 */
@Component
public class FachadaAdmin {

    @Autowired
    private ServiciosPlanta servPlanta;
    @Autowired
    private ServiciosEjemplar servEjemplar;

    @Autowired
    private ServiciosMensaje servMensaje;

    @Autowired
    private ServiciosPersona servPersona;

    @Autowired
    private ServiciosCredenciales servCredenciales;

    @Autowired
    @Lazy
    private Controlador controlador;

    @Autowired
    @Lazy
    private FachadaInvitado fachadaInvitado;

    @Autowired
    @Lazy
    private FachadaPersonal fachadaPersonal;

    private Scanner in = new Scanner(System.in);

    public void menuAdmin() {
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t\t------MENÚ DE ADMINISTRADOR------");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            System.out.println("\t\t\t\t\tSelecciona una opción:");
            System.out.println("\t\t\t\t\t1. Gestión de plantas");
            System.out.println("\t\t\t\t\t2. Gestión de ejemplares");
            System.out.println("\t\t\t\t\t3. Gestión de mensajes");
            System.out.println("\t\t\t\t\t4. Gestión de personas");
            System.out.println("\t\t\t\t\t5. CERRAR SESIÓN.");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        menuAdminPlantas();
                        break;
                    case 2:
                        menuAdminEjemplares();
                        break;
                    case 3:
                        menuAdminMensajes();
                        break;
                    case 4:
                        menuAdminPersonas();
                        break;
                    case 5:
                        controlador.cerrarSesion();
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }

    public void menuAdminPlantas() {
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t\tSelecciona una opción:");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            System.out.println("\t\t\t\t\t1. Ver plantas");
            System.out.println("\t\t\t\t\t2. Crear nueva planta");
            System.out.println("\t\t\t\t\t3. Modificar datos de una planta");
            System.out.println("\t\t\t\t\t4. Borrar una planta");
            System.out.println("\t\t\t\t\t5. Volver al menú principal");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        fachadaInvitado.verTodasPlantas();
                        break;
                    case 2:
                        nuevaPlanta();
                        break;
                    case 3:
                        menuAdminModificarPlantas();
                        break;
                    case 4:
                    	borrarPlanta();
                    	break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }

    public void menuAdminModificarPlantas() {
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t\tSelecciona una opción:");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            System.out.println("\t\t\t\t\t1. Modificar nombre común");
            System.out.println("\t\t\t\t\t2. Modificar nombre científico");
            System.out.println("\t\t\t\t\t3. Volver al menú de plantas");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 3) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        modificarNombreComun();
                        break;
                    case 2:
                        modificarNombreCientifico();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 3);
    }

    public void menuAdminEjemplares() {
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t\tSelecciona una opción:");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            System.out.println("\t\t\t\t\t1. Registrar nuevo ejemplar");
            System.out.println("\t\t\t\t\t2. Filtrar ejemplares por tipo de planta");
            System.out.println("\t\t\t\t\t3. Ver mensajes de un ejemplar");
            System.out.println("\t\t\t\t\t4. Borrar un ejemplar");
            System.out.println("\t\t\t\t\t5. Volver al menú principal");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        nuevoEjemplar();
                        break;
                    case 2:
                       // fachadaPersonal.filtrarEjemplaresPorCodigoPlanta();
                        break;
                    case 3:
                        verMensajesEjemplar();
                        break;
                    case 4:
                    	fachadaPersonal.borrarEjemplar();
                    	break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }
    public void menuAdminPersonas() {
		int opcion = 0;
		do {
			System.out.println("\t\t\t\t\tSelecciona una opción:");
			System.out.println("\t\t\t\t\t───────────────────────────────");
			System.out.println("\t\t\t\t\t1. Registrar nueva persona");
			System.out.println("\t\t\t\t\t2. Ver todas las personas");
			System.out.println("\t\t\t\t\t3. Borrar una persona");
			System.out.println("\t\t\t\t\t4. Volver al menú principal");
			System.out.println("\t\t\t\t\t───────────────────────────────");
			try {
				opcion = in.nextInt();
				if (opcion < 1 || opcion > 4) {
					System.out.println("Opción incorrecta");
					continue;
				}
				switch (opcion) {
				case 1:
					nuevaPersona();
					break;
				case 2:
					verTodasPersonas();
					break;
				case 3:
					borrarPersona();
				}
			} catch (InputMismatchException e) {
				System.out.println("Debes ingresar un número");
				in.nextLine();
				opcion = 0;
			}
		} while (opcion != 4);
	}

    public void menuAdminMensajes() {
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t\tSelecciona una opción:");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            System.out.println("\t\t\t\t\t1. Nuevo mensaje");
            System.out.println("\t\t\t\t\t2. Ver mensajes");
            System.out.println("\t\t\t\t\t3. Volver al menú principal");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 3) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        fachadaPersonal.nuevoMensaje();
                        break;
                    case 2:
                        menuAdminVerMensajes();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 3);
    }

    public void menuAdminVerMensajes() {
        int opcion = 0;
        do {
            System.out.println("\t\t\t\t\tSelecciona una opción:");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            System.out.println("\t\t\t\t\t1. Ver todos los mensajes");
            System.out.println("\t\t\t\t\t2. Ver mensajes por persona");
            System.out.println("\t\t\t\t\t3. Ver mensajes por rango de fechas");
            System.out.println("\t\t\t\t\t4. Ver mensajes por tipo de planta");
            System.out.println("\t\t\t\t\t5. Volver al menú de mensajes");
            System.out.println("\t\t\t\t\t───────────────────────────────");
            try {
                opcion = in.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.println("Opción incorrecta");
                    continue;
                }
                switch (opcion) {
                    case 1:
                        verTodosMensajes();
                        break;
                    case 2:
                        fachadaPersonal.verMensajesPersona();
                        break;
                    case 3:
                        fachadaPersonal.verMensajesFechas();
                        break;
                    case 4:
                        fachadaPersonal.verMensajesTipoPlanta();
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes ingresar un número");
                in.nextLine();
                opcion = 0;
            }
        } while (opcion != 5);
    }

    /**
	 * Método para crear una nueva planta, con sus validaciones y controlando las
	 * excepciones que pueden surgir
	 * 
	 */
	public Planta nuevaPlanta() {
		in.nextLine();
		Planta p;
		boolean datosPlantaCorrectos = false;
		do {
			p = new Planta();
			System.out.print("Código (mayúsculas): ");
			try {
				String codigo = in.nextLine().trim().toUpperCase();
				boolean correcto = servPlanta.validarCodigo(codigo);
				boolean existe = servPlanta.codigoExistente(codigo);
				if (!correcto) {
					System.out.println("El formato del código no es correcto");
					continue;
				}
				if (existe) {
					System.out.println("El código ya existe para una planta");
					continue;
				}
				p.setCodigo(codigo);
				System.out.print("Nombre común: ");
				String nombreComun = in.nextLine().trim();
				p.setNombreComun(nombreComun);
				System.out.print("Nombre científico: ");
				String nombreCientifico = in.nextLine().trim();
				p.setNombreCientifico(nombreCientifico);
				datosPlantaCorrectos = servPlanta.validarPlanta(p);
				if (!datosPlantaCorrectos) {
					System.out.println("Los datos que has introducido no son correctos");
				}
			} catch (Exception ex) {
				System.out.println("Error durante la entrada de datos: " + ex.getMessage());
				datosPlantaCorrectos = false;
			}
		} while (!datosPlantaCorrectos);
		try {
	        servPlanta.insertar(p);
	        System.out.println("Planta insertada");
	    } catch (Exception ex) {
	        System.out.println("Error al insertar la planta: " + ex.getMessage());
	    }

		return p;
	}
	
	/**
	 * Método para crear un nuevo ejemplar, con sus validaciones
	 * y posibles excepciones que puedan surgir
	 * 
	 */
	public Ejemplar nuevoEjemplar() {
	    Ejemplar e = null;
	    Mensaje m = null;
	    boolean correcto = false;
	    ArrayList<Planta> plantas = (ArrayList<Planta>) servPlanta.verTodas(); //Cargo en un ArrayList todas las plantas de la base de datos para mostrarlas una a una
        if (plantas == null || plantas.isEmpty()) {
            System.out.println("Lo siento, no hay plantas para mostrar en la base de datos");
        }
        System.out.println("Todas las plantas: ");
        System.out.println();
        for (Planta p : plantas) {
            System.out.println(p);
            System.out.println();
        }
	    do {
	        try {
	            System.out.print("Código de la planta del ejemplar: ");
	            String codigoPlanta = in.nextLine().trim().toUpperCase();
	            boolean codigoValido = servPlanta.validarCodigo(codigoPlanta);
	            boolean plantaExiste = servPlanta.codigoExistente(codigoPlanta);
	            if (!codigoValido) {
	                System.out.println("El formato del código no es correcto");
	                continue;
	            }
	            if (!plantaExiste) {
	                System.out.println("No existe una planta con ese codigo");
	                continue;
	            }
	            Planta p = servPlanta.buscarPorCodigo(codigoPlanta);
	            e = new Ejemplar();
	            e.setPlanta(p);
	            e.setNombre(codigoPlanta); //Le pongo este nombre momentaneo para poder insertarlo en la base de datos
	            servEjemplar.insertar(e);
	            e.setNombre(e.getPlanta().getCodigo() + "_" + e.getId()); //Le añado el nombre verdadero al ejemplar
	            servEjemplar.cambiarNombre(e.getId(), e.getNombre());//Lo persisto en la base de datos con su nombre de verdad
	            System.out.println("Ejemplar insertado con ID: " + e.getId());
	            String mensajeTexto = "Añadido el ejemplar " + e.getNombre();
	            LocalDateTime fechaHora = LocalDateTime.now();
	            String usuarioAutenticado = controlador.getUsuarioAutenticado();
	            Persona persona = servCredenciales.buscarPersonaPorUsuario(usuarioAutenticado);
	            if (persona == null) {
	                System.out.println("Error: No se ha encontrado la persona autenticada");
	            } else {
	                m = new Mensaje(fechaHora, mensajeTexto, persona, e); //Inserto un mensaje de creación del ejemplar
	                servMensaje.insertar(m);
	                System.out.println("Mensaje de creación del ejemplar añadido");
	            }
	            correcto = true;
	        } catch (Exception ex) {
	            System.out.println("Error durante la creación del ejemplar: " + ex.getMessage());
	            correcto = false;
	        }
	    } while (!correcto);
	    return e;
	}


	/**
	 * Método para crear una nueva persona, con sus validaciones y controlando las
	 * excepciones que pueden surgir
	 * 
	 */
	public Persona nuevaPersona() {
	    in.nextLine();
	    Persona pers;
	    Credenciales c;
	    boolean correcto = false;
	    boolean emailValido = false;
	    boolean usuarioValido = false;
	    boolean contraseñaValida = false;
	    String usuario = "";
	    String contraseña = "";
	    do {
	        emailValido = false;
	        usuarioValido = false;
	        contraseñaValida = false;
	        pers = new Persona();  //Creo un objeto de tipo Persona
	        c = new Credenciales();//Creo un objeto de tipo Credenciales
	        System.out.print("Nombre: ");
	        String nombre = in.nextLine().trim();
	        pers.setNombre(nombre);//Asigno el nombre a la persona
	        String email = "";
	        do {
	            System.out.print("Email: ");
	            email = in.nextLine().trim();
	            pers.setEmail(email);//Asigno el email a la persona
	            if (servPersona.emailExistente(email)) {
	                System.out.println("El email que has introducido ya está registrado");
	            } else {
	                emailValido = true;
	            }
	        } while (!emailValido);
	        do {
	            System.out.print("Usuario: ");
	            usuario = in.nextLine();
	            if (usuario.equalsIgnoreCase("ADMIN")) {
	                System.out.println("El usuario admin ya está ocupado.");
	            } else if (servCredenciales.usuarioExistente(usuario) || usuario.length() < 3 || usuario.contains(" ")) {
	                System.out.println("El usuario que has introducido ya está registrado o no cumple con los requisitos mínimos");
	            } else {
	                usuarioValido = true;
	                c.setUsuario(usuario);//Asigno el usuario a esas credenciales
	            }
	        } while (!usuarioValido);
	        do {
	            System.out.print("Contraseña: ");
	            contraseña = in.nextLine().trim();
	            if (servCredenciales.validarContraseña(contraseña) == false) {
	                System.out.println("La contraseña debe tener al menos 8 caracteres e incluir al menos un carácter especial como un punto o una coma");
	            } else {
	                contraseñaValida = true;
	                c.setPassword(contraseña);
	            }
	        } while (!contraseñaValida);
	        c.setPersona(pers);  //Vinculo las credenciales a la persona
	        pers.setCredenciales(c);//Vinculo la persona a las credenciales
	        //Valido la persona
	        correcto = servPersona.validarPersona(pers);
	        if (!correcto) {
	            System.out.println("Los datos que has introducido no son correctos.");
	        }
	    } while (!correcto);
	    try {
	        servPersona.insertar(pers);//Inserto la persona y las credenciales a través del cascade
	        System.out.println("Persona y credenciales insertadas correctamente");
	    } catch (Exception ex) {
	        System.out.println("Error al insertar la persona nueva: " + ex.getMessage());
	    }
	    return pers;
	}

	/**
	 * Método para listar todas las personas por pantalla
	 * 
	 */
	public void verTodasPersonas() {
		ArrayList<Persona> personas = (ArrayList<Persona>) servPersona.verTodos(); //Cargo todas las personas en un ArrayList para sacarlas por pantalla
		if (personas == null || personas.isEmpty()) {
			System.out.println("Lo siento, no hay personas para mostrar en la base de datos");
			return;
		}
		System.out.println("Todo el personal: ");
		System.out.println();
		for (Persona pers : personas) {
			System.out.println(pers);
			System.out.println();
		}
	}

	/**
	 * Método para listar todos los mensajes por pantalla
	 * 
	 */
	public void verTodosMensajes() {
		ArrayList<Mensaje> mensajes = (ArrayList<Mensaje>) servMensaje.verTodos(); //Cargo todos los mensajes en un ArrayList para sacarlos por pantalla
		if (mensajes == null || mensajes.isEmpty()) {
			System.out.println("Lo siento, no hay mensajes para mostrar en la base de datos");
			return;
		}
		System.out.println("Todos los mensajes: ");
		System.out.println();
		for (Mensaje m : mensajes) {
			System.out.println(m);
			System.out.println();
		}
	}
	
	/**
	 * Método para modificar el nombre común de un tipo de planta, 
	 * pasándole el código de esa planta de la cual se quiere cambiar
	 * 
	 */
	public void modificarNombreComun() {
	    in.nextLine();
	    String codigo = "";
	    boolean valido = false;
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
	    do {
	        System.out.print("Introduce el código de la planta de la que quieres modificar el nombre común: ");
	        codigo = in.nextLine().trim().toUpperCase();
	        valido = servPlanta.validarCodigo(codigo);
	        if (!valido) {
	            System.out.println("El código de la planta que has introducido no es válido");
	        }
	    } while (!valido);
	    boolean existe = servPlanta.codigoExistente(codigo);
	    if (!existe) {
	        System.out.println("El código de la planta que has introducido no existe en la base de datos");
	        return;
	    }
	    System.out.print("Introduce el nuevo nombre común de la planta: ");
	    String nuevoNombreComun = in.nextLine().trim();
	    try {
	        boolean actualizado = servPlanta.actualizarNombreComun(codigo, nuevoNombreComun); //Llamo al método para actualizar el nombre común
	        if (actualizado) {
	            System.out.println("El nombre común de la planta con código " + codigo + " ha sido actualizado, ahora el nombre es: " + nuevoNombreComun);
	        } else {
	            System.out.println("Error al intentar actualizar el nombre común");
	        }
	    } catch (Exception ex) {
	        System.out.println("Error al actualizar el nombre común: " + ex.getMessage());
	    }
	}

	/**
	 * Método para modificar el nombre científico de un tipo de planta, 
	 * pasándole el código de esa planta de la cual se quiere cambiar
	 * 
	 */
	public void modificarNombreCientifico() {
	    in.nextLine();
	    String codigo = "";
	    boolean valido = false;
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
	    do {
	        System.out.print("Introduce el código de la planta de la que quieres modificar el nombre científico: ");
	        codigo = in.nextLine().trim().toUpperCase();
	        valido = servPlanta.validarCodigo(codigo);
	        if (!valido) {
	            System.out.println("El código de la planta que has introducido no es válido");
	        }
	    } while (!valido);
	    boolean existe = servPlanta.codigoExistente(codigo);
	    if (!existe) {
	        System.out.println("El código de la planta que has introducido no existe en la base de datos");
	        return; 
	    }
	    System.out.print("Introduce el nuevo nombre científico de la planta: ");
	    String nuevoNombreCientifico = in.nextLine().trim();
	    try {
	        boolean actualizado = servPlanta.actualizarNombreCientifico(codigo, nuevoNombreCientifico); //Llamo al método para actualizar el nombre científico
	        if (actualizado) {
	            System.out.println("El nombre científico de la planta con código " + codigo + " ha sido actualizado, ahora el nombre es: " + nuevoNombreCientifico);
	        } else {
	            System.out.println("Error al intentar actualizar el nombre científico");
	        }
	    } catch (Exception ex) {
	        System.out.println("Error al actualizar el nombre científico: " + ex.getMessage());
	    }
	}

	/**
	 * Método para ver los mensajes de un ejemplar,
	 * a través de su id
	 * 
	 */
	public void verMensajesEjemplar() {
		ArrayList<Ejemplar> ejemplares = (ArrayList<Ejemplar>) servEjemplar.verTodos(); //Cargo todos los ejemplares
	    System.out.println("Todos los ejemplares: ");
	    System.out.println();
	    for (Ejemplar e : ejemplares) {
	        System.out.println(e);
	        System.out.println();
	    }
	    System.out.print("Introduce el id del ejemplar para ver sus mensajes: ");
	    try {
	        String id = in.nextLine().trim();
	        if (id.isEmpty()) {
	            System.out.println("No has introducido un id válido");
	            return;
	        }
	        long idEjemplar;
	        try {
	            idEjemplar = Long.parseLong(id);
	        } catch (NumberFormatException e) {
	            System.out.println("El id introducido no es válido");
	            return;
	        }
	        ArrayList<Mensaje> mensajes = servMensaje.verMensajesPorEjemplar(idEjemplar);
	        if (mensajes == null || mensajes.isEmpty()) {
	            System.out.println("No hay mensajes para el ejemplar con id: " + idEjemplar);
	        } else {
	            System.out.println("Mensajes del ejemplar con id " + idEjemplar + ":");
	            System.out.println();
	            for (Mensaje mensaje : mensajes) {
	                System.out.println(mensaje);
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error al intentar obtener los mensajes: " + e.getMessage());
	    }
	}

	
	/**
	 * Método para borrar una persona 
	 * introduciendo su id
	 * Este método no se pedía, pero he decidirlo incluirlo como mejora, ya que así resulta más fácil para manipular el programa,
	 * el usuario administrador puede eliminar personas de la base de datos, eliminando también sus credenciales gracias al borrado en cascada
	 * 
	 */
	public void borrarPersona() {
		verTodasPersonas();
		System.out.println();
        System.out.print("Introduce el id de la persona a eliminar: ");
        try {
            Long idPersona = in.nextLong();
            in.nextLine();
            boolean borrada = servPersona.borrarPersona(idPersona);
            if (borrada) {
                System.out.println("Persona con el id "+ idPersona + " borrada del sistema");
            } else {
                System.out.println("No se encontró una persona con ese id");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Debes introducir un id válido");
            in.nextLine();
        } catch (Exception e) {
            System.out.println("Error durante el borrado de la persona: " + e.getMessage());
        }
    }
	
	/**
	 * Método para borrar una planta introduciendo su codigo
	 * Este método no se pedía, pero he decidirlo incluirlo como mejora, ya que así resulta más fácil para manipular el programa sin tener que
	 * entrar a la base de datos cada vez que quiero borrar algo,
	 * el usuario administrador puede eliminar plantas de la base de datos, eliminando también sus ejemplares gracias al borrado en cascada
	 * 
	 */
	public void borrarPlanta() {
		in.nextLine();
	    ArrayList<Planta> plantas = (ArrayList<Planta>) servPlanta.verTodas();
	    if (plantas == null || plantas.isEmpty()) {
	        System.out.println("Lo siento, no hay plantas para mostrar en la base de datos");
	        return;
	    }
	    System.out.println("Todas las plantas:");
	    for (Planta p : plantas) {
	        System.out.println(p);
	        System.out.println();
	    }
	    System.out.print("Introduce el código de la planta que quieres borrar: ");
	    try {
	        String codigo = in.nextLine().trim().toUpperCase();
	        boolean borrada = servPlanta.borrarPlanta(codigo);
	        if (borrada) {
	            System.out.println("Planta con código " + codigo + " borrada");
	        } else {
	            System.out.println("No se encontró ninguna planta con el código: " + codigo);
	        }
	    } catch (InputMismatchException e) {
	        System.out.println("Error: Debes introducir un código válido");
	    } catch (Exception e) {
	        System.out.println("Error durante el borrado: " + e.getMessage());
	    }
	}
	

}