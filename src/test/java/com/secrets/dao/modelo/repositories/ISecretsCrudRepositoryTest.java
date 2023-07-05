package com.secrets.dao.modelo.repositories;

import com.secrets.dao.modelo.entitys.EntitySecreto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
class ISecretsCrudRepositoryTest {

    //-- Test: Repositorios
    //-- Aquí estamos probando directamente la Interfaz con JPA
    //-- Probamos los métodos directamente sin pasar por el Services

    @Autowired
    ISecretsCrudRepository iSecretsCrudRepository;


    //------------------------------------------------------------------------------------------------
    @Test
    @Order(1)
    @DisplayName("listarSecretosByIdCategoria() - Encuentra Información")
    void listarSecretosByIdCategoria() {
        List<EntitySecreto> lista = iSecretsCrudRepository.listarSecretosByIdCategoria(1L);
        Assertions.assertFalse(lista.isEmpty());
    }


}