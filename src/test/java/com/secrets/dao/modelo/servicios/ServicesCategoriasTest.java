package com.secrets.dao.modelo.servicios;

import com.secrets.dao.Datos;
import com.secrets.dao.modelo.entitys.EntityCategoria;
import com.secrets.dao.modelo.repositories.ICategoriasCrudRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServicesCategoriasTest {

    //-- Inyección
    @MockBean
    private ICategoriasCrudRepository iCategoriasCrudRepository;


    @Autowired
    @Qualifier("servicesCategorias")
    private IServicesCategorias iServicesCategorias;



    //------------------------------------------------------------------------------------------------

    @Test
    @Order(1)
    @DisplayName("buscarCategoriaById() - Encuentra")
    void buscarCategoriaById() {

        //-- Datos
        Mockito.when(this.iCategoriasCrudRepository.findById(Mockito.any())).thenReturn(Optional.of(Datos.categoriaAmigos));

        //-- Servicio
        EntityCategoria entityCategoria=this.iServicesCategorias.buscarCategoriaById(1L);

        //-- Test
        Assertions.assertNotNull(entityCategoria);
    }

    @Test
    @Order(1)
    @DisplayName("buscarCategoriaById() - No Encuentra")
    void buscarCategoriaByIdNoEncuentra() {

        //-- Datos
        Mockito.when(this.iCategoriasCrudRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));

        //-- Repository
        EntityCategoria entityCategoria=this.iServicesCategorias.buscarCategoriaById(1L);

        //-- Test
        Assertions.assertEquals(null, entityCategoria);
    }

    //------------------------------------------------------------------------------------------------

    @Test
    @Order(2)
    @DisplayName("listarCategorias() - Encuentra ")
    void listarCategorias() {
        //-- Datos
        Mockito.when(this.iCategoriasCrudRepository.findAll()).thenReturn(Datos.getListarCategorias());
        //-- Repository
        List<EntityCategoria> list = this.iServicesCategorias.listarCategorias();

        //-- Test
        Assertions.assertFalse(list.isEmpty());
    }
    @Test
    @Order(2)
    @DisplayName("listarCategorias() - No Encuentra ")
    void listarCategoriasNoEncuentra() {
        //-- Datos
        Mockito.when(this.iCategoriasCrudRepository.findAll()).thenReturn(null);
        //-- Repository
        List<EntityCategoria> list = this.iServicesCategorias.listarCategorias();

        //-- Test
        Assertions.assertTrue(list.isEmpty());
    }
}