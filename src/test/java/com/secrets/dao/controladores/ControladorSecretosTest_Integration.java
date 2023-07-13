package com.secrets.dao.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secrets.dao.modelo.entitys.EntitySecreto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControladorSecretosTest_Integration {

    //-- Inyección de Dependencies
    @Autowired
    private TestRestTemplate restTemplate;

    //---
    private ObjectMapper objectMapper=new ObjectMapper();


    //------------------------------------------------------------------------------------------------

    @Test
    @Order(1)
    @DisplayName("listarSecretos() - Encuentra/Vacío")
    void listarSecretos() {

        //-- Consumir endPoint
        ResponseEntity<EntitySecreto[]> response= this.restTemplate.getForEntity("/listar/",EntitySecreto[].class);

        //-- Realizamos pruebas
        List<EntitySecreto> body= List.of(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        Assertions.assertEquals(false, body==null);
    }

    //------------------------------------------------------------------------------------------------

    @Test
    @Order(2)
    @DisplayName("listarSecretosPaginado() - Pendiente")
    void listarSecretosPaginado() {
    }
    //------------------------------------------------------------------------------------------------

    @Test
    @Order(3)
    @DisplayName("listarSecretosByIdCategoria() - Encuentra/Vacío")
    void listarSecretosByIdCategoria() {

        //-- Consumir endPoint
        ResponseEntity<EntitySecreto[]> response = this.restTemplate.getForEntity("/listar/categoria/1", EntitySecreto[].class);

        //-- Test
        List<EntitySecreto> body= List.of(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        Assertions.assertEquals(false, body==null);

        body.forEach( secreto ->{
            Assertions.assertEquals(1, secreto.getEntityCategoria().getId());
            Assertions.assertEquals("amigos", secreto.getEntityCategoria().getNombre());
        });

    }

    @Test
    @Order(3)
    @DisplayName("listarSecretosByIdCategoria() - NoEncuentraCategoria")
    void listarSecretosByIdCategoria_NoEncuentraCategoria() {

        //-- Consumir endPoint
        ResponseEntity<?> response = this.restTemplate.getForEntity("/listar/categoria/100", Object.class);

        //-- Test
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON,response.getHeaders().getContentType());
    }

    //------------------------------------------------------------------------------------------------

    @Test
    @Order(4)
    @DisplayName("buscarSecretoById() - Lo encuentra")
    void buscarSecretoById() {

        //-- Consumir endPoin
        ResponseEntity<EntitySecreto> response = this.restTemplate.getForEntity("/buscar/1", EntitySecreto.class);

        //-- Test
        EntitySecreto body= response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        Assertions.assertEquals(false, body==null );

    }
    //------------------------------------------------------------------------------------------------

    @Test
    void guardarEditarSecreto() {

    }
    //------------------------------------------------------------------------------------------------

    @Test
    void editarSecreto() {
    }
    //------------------------------------------------------------------------------------------------

    @Test
    void guardarEditarSecretoAdm() {
    }
    //------------------------------------------------------------------------------------------------

    @Test
    void eliminarSecretoById() {
    }
}