package com.alejandromg.tarea3dwes24;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.alejandromg.tarea3dwes24.vista.FachadaInvitado;

public class Principal implements CommandLineRunner {
	
	 @Autowired
	 private FachadaInvitado fachadaInvitado;
	@Override
	public void run(String... args) throws Exception {
		fachadaInvitado.menuInvitado();
    }
}



