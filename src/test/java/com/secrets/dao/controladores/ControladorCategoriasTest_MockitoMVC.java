package com.secrets.dao.controladores;

import com.secrets.dao.Datos;
import com.secrets.dao.modelo.servicios.IServicesCategorias;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
class ControladorCategoriasTest_MockitoMVC {

    //-- Inyecciónes
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServicesCategorias iServicesCategorias;


    //------------------------------------------------------------------------------------------------
    @Test
    @Order(1)
    @DisplayName("listarCategorias() - Regresa Valores")
    void listarCategorias() throws Exception {

        //--Datos
        Mockito.when(this.iServicesCategorias.listarCategorias()).thenReturn(Datos.getListarCategorias());

        //-- Servvicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categorias/listar/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }


}