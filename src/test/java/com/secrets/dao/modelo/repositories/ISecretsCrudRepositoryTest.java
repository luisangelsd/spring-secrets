package com.secrets.dao.modelo.repositories;


import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import com.secrets.dao.modelo.entitys.EntitySecreto;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ISecretsCrudRepositoryTest {

    //-- Test: Repositorios
    //-- Aquí estamos probando directamente la Interfaz con JPA
    //-- Probamos los métodos directamente sin pasar por el Services

    @Autowired
    private ISecretsCrudRepository iSecretsCrudRepository;


    //------------------------------------------------------------------------------------------------
    @Test
    @Order(1)
    @DisplayName("listarSecretosByIdCategoria() - Encuentra Información")
    void listarSecretosByIdCategoria() {
    	
    	//-- Datos
        List<EntitySecreto> lista = iSecretsCrudRepository.listarSecretosByIdCategoria(1L);
        
        //-- Test
        Assertions.assertFalse(lista.isEmpty());
    }

    @Order(1)
    @DisplayName("listarSecretosByIdCategoria() - No Encuentra Información")
    void listarSecretosByIdCategoriaNoEncuentra() {

    	//-- Datos
        List<EntitySecreto> lista = iSecretsCrudRepository.listarSecretosByIdCategoria(1000L);

        //-- Test
        Assertions.assertTrue(lista.isEmpty());
    }
    


}