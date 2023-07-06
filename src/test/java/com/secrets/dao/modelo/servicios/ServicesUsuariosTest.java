package com.secrets.dao.modelo.servicios;

import com.secrets.dao.Datos;
import com.secrets.dao.modelo.repositories.IUsuariosCrudRepository;
import com.secrets.dao.oauth2.services.entitys.EntityUsuario;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

//-- Inyección: SpringBoot

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServicesUsuariosTest {

    //-- Inyección
    @MockBean
    private IUsuariosCrudRepository iUsuariosCrudRepository;

    @Autowired
    @Qualifier("servicesUsuarios")
    private IServicesUsuarios iServicesUsuarios;

    //-------------------------------------------------------------------------------------------
    @Test
    @Order(1)
    @DisplayName("buscarUsuarioByUsername() - Encontrado")
    void buscarUsuarioByUsername() {

        //-- Datos
        Mockito.when(this.iUsuariosCrudRepository.buscarUsuarioByUsername(Mockito.any())).thenReturn(Datos.usuario1);

        //-- Repository
        EntityUsuario entityUsuario=this.iServicesUsuarios.buscarUsuarioByUsername("usuario1");

        //-- Test
        Assertions.assertNotNull(entityUsuario);

    }

    @Test
    @Order(1)
    @DisplayName("buscarUsuarioByUsername() - No Encontrado")
    void buscarUsuarioByUsernameNoEncontrado() {

        //-- Datos
        Mockito.when(this.iUsuariosCrudRepository.buscarUsuarioByUsername(Mockito.any())).thenReturn(null);

        //-- Repository
        EntityUsuario entityUsuario=this.iServicesUsuarios.buscarUsuarioByUsername("usuario1");

        //-- Test
        Assertions.assertEquals(null, entityUsuario);

    }


    //-------------------------------------------------------------------------------------------
    @Test
    @Order(2)
    @DisplayName("editarUrlImagenPerfilUsuario() - Edita Ok")
    void editarUrlImagenPerfilUsuario() {

        //-- Datos: Indica que regresa vacio
        Mockito.doNothing().when(this.iUsuariosCrudRepository).editarUrlImagenPerfilUsuario(Mockito.any(), Mockito.any());

        //-- Repository: Consume el servicio
        this.iServicesUsuarios.editarUrlImagenPerfilUsuario("user1","https://sandovalguicho.com");

    }
}