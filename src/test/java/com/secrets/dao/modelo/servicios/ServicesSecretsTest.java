package com.secrets.dao.modelo.servicios;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.secrets.dao.Datos;
import com.secrets.dao.modelo.entitys.EntitySecreto;
import com.secrets.dao.modelo.repositories.ISecretsCrudRepository;

//-- Inyección: SpringBoot

@SpringBootTest
public class ServicesSecretsTest {

	//-- Inyección
	@MockBean
	private ISecretsCrudRepository iSecretsRepository;
	
	
	@Autowired
	@Qualifier("servicesSecrets")
	private IServicesSecrets iServiceSecrets;
	
	
	@Test
	void listarSecretosTest() {
		
		//-- Simular Repository
		Mockito.when( this.iSecretsRepository.findAll()).thenReturn( Datos.getListarUsuariosNull() );
		
		//-- Ejecutar Servicio
		List<EntitySecreto> lista= this.iServiceSecrets.listarSecretos();
		
		lista.forEach(System.out::print);
		
		//-- Tests
		assertNotNull(lista);
		
		
	}
	
	
	
}
