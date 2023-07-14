package com.secrets.dao.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secrets.dao.Datos;
import com.secrets.dao.modelo.entitys.EntitySecreto;
import org.apache.coyote.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.time.LocalDate;
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

    @Test
    @Order(4)
    @DisplayName("buscarSecretoById() - No lo encuentra")
    void buscarSecretoById_NoLoEncontro() {

        //-- Consumir endPoin
        ResponseEntity<EntitySecreto> response = this.restTemplate.getForEntity("/buscar/100", EntitySecreto.class);

        //-- Test
        EntitySecreto body= response.getBody();
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        Assertions.assertEquals(false, body==null );

    }

    //------------------------------------------------------------------------------------------------

    @Test
    @Order(5)
    @DisplayName("guardarSecreto() - Guardado")
    void guardarSecreto() {

        //-- Datos
        EntitySecreto secreto = Datos.secreto1;
        secreto.setSecreto("Este es un nuevo secreto");

        //-- Consumir endPoint
        ResponseEntity<EntitySecreto> response = this.restTemplate.postForEntity("/guardar/categoria-id/1", secreto,  EntitySecreto.class);

        //--  Test
        secreto=response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        Assertions.assertFalse(secreto==null);

    }

    @Test
    @Order(5)
    @DisplayName("guardarSecreto() - ¡Categoria no Existe!")
    void guardarSecreto_categoriaNoExiste() {

        //-- Datos
        EntitySecreto secreto = Datos.secreto1;
        secreto.setSecreto("Este es un nuevo secreto");

        //-- Consumir endPoint
        ResponseEntity<EntitySecreto> response = this.restTemplate.postForEntity("/guardar/categoria-id/100", secreto,  EntitySecreto.class);

        //--  Test
        Assertions.assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

    }

    @Test
    @Order(5)
    @DisplayName("guardarSecreto() - ¡Bad Request!")
    void guardarSecreto_badRequest() {

        //-- Datos
        EntitySecreto secreto = new EntitySecreto();

        //-- Consumir endPoint
        ResponseEntity<EntitySecreto> response = this.restTemplate.postForEntity("/guardar/categoria-id/100", secreto,  EntitySecreto.class);

        //--  Test
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

    }
    //------------------------------------------------------------------------------------------------

    //-- Nota: Este tiene una validación en la cual la fecha del registro con la fecha de actualizacion deben de ser las mismas
    //-- asegurate de cambiar la fecha del secreto que estes testeando en el import.sql :)
    @Test
    @Order(5)
    @DisplayName("editarSecreto - OK")
    void editarSecreto() {

        //-- Datos
        EntitySecreto secreto = Datos.secreto1;
        secreto.setSecreto("¡Este secreto fue editado!");

        //-- Consumir endPoint editar y despues buscar
        ResponseEntity<EntitySecreto> response =
                this.restTemplate.exchange ("/editar/1/categoria-id/1", HttpMethod.PUT, new HttpEntity<>(secreto), EntitySecreto.class);

        //-- Test
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        Assertions.assertEquals("¡Este secreto fue editado!", response.getBody().getSecreto());
    }


    //------------------------------------------------------------------------------------------------

    //-- Este necesita un Tocken asegura de tener uno valido
    @Test
    @Order(6)
    @DisplayName("editarSecretoAdm() - Funciona")
    void editarSecretoAdm() {

        //-- Datos
        EntitySecreto secreto = Datos.secreto1;
        secreto.setSecreto("¡Este secreto fue editado!");
        secreto.setfCreacion(LocalDate.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(Datos.tokenAdmin);

        //-- Consumir endPoint
        ResponseEntity<EntitySecreto> response = this.restTemplate.exchange("/adm/editar/1/categoria-id/1",HttpMethod.PUT,new HttpEntity<>(secreto, headers), EntitySecreto.class);

        //-- Test
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        Assertions.assertEquals("¡Este secreto fue editado!", response.getBody().getSecreto());
    }

    //------------------------------------------------------------------------------------------------

    @Test
    @Order(7)
     @DisplayName("eliminarSecretoById() - Eliminado")
    void eliminarSecretoById() {
        //-- Datos
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(Datos.tokenAdmin);

        //-- Consumir endPoint
        ResponseEntity<EntitySecreto> response = this.restTemplate.exchange("/adm/eliminar/1",HttpMethod.DELETE,new HttpEntity<>(headers), EntitySecreto.class);

        //Tests
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        Assertions.assertTrue(response.hasBody());
        Assertions.assertEquals(1, response.getBody().getId());
    }
}