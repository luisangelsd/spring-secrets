package com.secrets.dao.modelo.repositories;

import com.secrets.dao.oauth2.services.entitys.EntityUsuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class IUsuariosCrudRepositoryTest {

    //-- Test: Repositorios
    //-- Aquí estamos probando directamente la Interfaz con JPA
    //-- Probamos los métodos directamente sin pasar por el Services

    //-- Inyección
    @Autowired
    private IUsuariosCrudRepository iUsuariosCrudRepository;


    //------------------------------------------------------------------------------------------------

    @Test
    @Order(1)
    @DisplayName("buscarUsuarioByUsername() - Lo Encuentra")
    void buscarUsuarioByUsername(){

        //-- Servicio
        EntityUsuario entityUsuario = this.iUsuariosCrudRepository.buscarUsuarioByUsername("admin");

        //-- Test
        Assertions.assertNotNull(entityUsuario);

    }

    @Test
    @Order(1)
    @DisplayName("buscarUsuarioByUsername() - No lo Encuentra")
    void buscarUsuarioByUsernameNoEncuenta(){

        //-- Servicio
        EntityUsuario entityUsuario = this.iUsuariosCrudRepository.buscarUsuarioByUsername("no-existe");

        //-- Test
        Assertions.assertEquals(null, entityUsuario);

    }

    //------------------------------------------------------------------------------------------------

    @Test //-- No lo pude testear porque regresa un void
    @Order(2)
    @DisplayName("editarUrlImagenPerfilUsuario() - Editada con Exito")
    void editarUrlImagenPerfilUsuario(){

        //-- Servicio
        this.iUsuariosCrudRepository.editarUrlImagenPerfilUsuario("admim","https://sandovalguicho.com/imgs/img.png");
    }



}