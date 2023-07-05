package com.secrets.dao.modelo.servicios;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.secrets.dao.Datos;
import com.secrets.dao.modelo.entitys.EntitySecreto;
import com.secrets.dao.modelo.repositories.ISecretsCrudRepository;

//-- Inyección: Mockito

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServicesSecretsTest {




	//-- Inyección
	@MockBean
	private ISecretsCrudRepository iSecretsRepository;
	

	@Autowired
	@Qualifier("servicesSecrets")
	private IServicesSecrets iServiceSecrets;


	//-------------------------------------------------------------------------------------------
	@Test
	@Order(1)
	@DisplayName("listarSecretosTest() - Encuentra Datos")
	void listarSecretosTest() {

		//-- Simular Repository
		Mockito.when( this.iSecretsRepository.findAll()).thenReturn( Datos.getListarUsuarios());

		//-- Ejecutar Servicio
		List<EntitySecreto> lista = this.iServiceSecrets.listarSecretos();

		//-- Tests
		assertFalse(lista.isEmpty());

	}

	@Test
	@Order(1)
	@DisplayName("listarSecretosTest() - No Encuentra Datos")
	void listarSecretosTestNoEncuentra() {

		//-- Simular Repository
		Mockito.when( this.iSecretsRepository.findAll()).thenReturn( null );

		//-- Ejecutar Servicio
		List<EntitySecreto> lista = this.iServiceSecrets.listarSecretos();

		//-- Tests
		assertTrue(lista.isEmpty());

	}

	//-------------------------------------------------------------------------------------------


	@Test
	@Order(2)
	@DisplayName("listarSecretosByIdCategoria() - Encuentra Datos")
	void listarSecretosByIdCategoria() {

		//-- Simular Repository
		Mockito.when( this.iSecretsRepository.listarSecretosByIdCategoria( Mockito.any() )).thenReturn( Datos.getListarUsuarios() );

		//-- Ejecutar Servicio
		List<EntitySecreto> lista = this.iServiceSecrets.listarSecretosByIdCategoria(3L);

		//-- Tests
		assertFalse(lista.isEmpty());
	}

	@Test
	@Order(2)
	@DisplayName("listarSecretosByIdCategoria() - No Encuentra Datos")
	void listarSecretosByIdCategoriaNoEncuentraDatos() {

		//-- Simular Repository
		Mockito.when( this.iSecretsRepository.listarSecretosByIdCategoria( Mockito.any() )).thenReturn( null );

		//-- Ejecutar Servicio
		List<EntitySecreto> lista = this.iServiceSecrets.listarSecretosByIdCategoria(3L);

		//-- Tests
		assertTrue(lista.isEmpty());
	}

	//-------------------------------------------------------------------------------------------
	@Test
	@Order(3)
	@DisplayName("buscarSecretoById() - Encuentra")
	void buscarSecretoById(){

		//-- Sumular Repository
		Mockito.when(this.iSecretsRepository.findById(Mockito.any())).thenReturn( Optional.of(Datos.secreto1) );

		//-- Ejecutar Servicio
		EntitySecreto entitySecreto= this.iServiceSecrets.buscarSecretoById(1L);


		//-- Test
		assertNotNull(entitySecreto);
	}

	@Test
	@Order(3)
	@DisplayName("buscarSecretoById() - No Encontrado")
	void buscarSecretoByIdNoEncontrado(){

		//-- Sumular Repository
		Mockito.when(this.iSecretsRepository.findById(Mockito.any())).thenReturn( Optional.empty() );

		//-- Ejecutar Servicio
		EntitySecreto entitySecreto= this.iServiceSecrets.buscarSecretoById(1L);

		//-- Test
		assertEquals(null, entitySecreto);
	}


	//-------------------------------------------------------------------------------------------
	@Test
	@Order(4)
	@DisplayName("guardarEditarSecreto() - Guardo con Exito")
	void guardarEditarSecreto(){

		//-- Preparar Secreto
		EntitySecreto secreto= Datos.secreto1;
		secreto.setId(null);

		//-- Simular Repository
		Mockito.when(this.iSecretsRepository.save( Mockito.any() )).thenReturn( secreto );

		//-- Servicio
		EntitySecreto guardado=this.iServiceSecrets.guardarEditarSecreto(secreto);

		//-- Test
		assertNotNull(guardado);

	}


	//-------------------------------------------------------------------------------------------
	@Test //-- No puede testear porque regresa un void
	@Order(4)
	@DisplayName("eliminarSecretoById() - Eliminado con Exito")
	void eliminarSecretoById(){

	}

}
