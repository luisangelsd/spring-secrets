package com.secrets.dao.controladores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secrets.dao.Datos;
import com.secrets.dao.modelo.excepciones.RunTimeExceptionNotFound;
import com.secrets.dao.modelo.repositories.IUsuariosCrudRepository;
import com.secrets.dao.modelo.servicios.IServicesCategorias;
import com.secrets.dao.modelo.servicios.IServicesUsuarios;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControladorUsuariosTest {

    //-- Inyección
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServicesUsuarios iServicesUsuarios;


    ObjectMapper objectMapper = new ObjectMapper();


    //------------------------------------------------------------------------------------------------
    @Test
    @Order(1)
    @DisplayName("buscarUsuarioByUsername() - Encuentra")
    void buscarUsuarioByUsername() throws Exception {
        //-- Datos
        Mockito.when(this.iServicesUsuarios.buscarUsuarioByUsername(Mockito.any())).thenReturn(Datos.usuario1);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/ver/1")
                .header("Authorization","Bearer "+ Datos.tokenAdmin)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(1)
    @DisplayName("buscarUsuarioByUsername() - No Encuentra")
    void buscarUsuarioByUsernameNoEncuentra() throws Exception {
        //-- Datos
        Mockito.when(this.iServicesUsuarios.buscarUsuarioByUsername(Mockito.any())).thenReturn(null);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/ver/1")
                 .header("Authorization","Bearer "+ Datos.tokenAdmin)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    //------------------------------------------------------------------------------------------------
    @Test
    @Order(2)
    @DisplayName("editarUrlImagenPerfilUsuario() - Editada")
    void editarUrlImagenPerfilUsuario() throws Exception {


        //-- Datos:
        Mockito.when(this.iServicesUsuarios.buscarUsuarioByUsername(Mockito.any())).thenReturn(Datos.usuario1);
        Mockito.doNothing().when(this.iServicesUsuarios).editarUrlImagenPerfilUsuario(Mockito.any(),Mockito.any());

        //-- Creando: Imagen (File)
        File file = new File("C:\\Users\\luisitobonito\\Downloads\\img.jpg");
        MultipartFile multipartFile = new MockMultipartFile("img.jpg", new FileInputStream(file));


        //-- Creando: Params
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "username");


        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/usuarios/imagen-perfil/upload/")
                .file("archivo", multipartFile.getBytes())
                .params(params)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+ Datos.tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Order(2)
    @DisplayName("editarUrlImagenPerfilUsuario() - Usuario no encontrado")
    void editarUrlImagenPerfilUsuarioUsuarioNoEncontrado() throws Exception {


        //-- Datos:
        Mockito.when(this.iServicesUsuarios.buscarUsuarioByUsername(Mockito.any())).thenReturn(null);
        Mockito.doNothing().when(this.iServicesUsuarios).editarUrlImagenPerfilUsuario(Mockito.any(),Mockito.any());

        //-- Creando: Imagen (File)
        File file = new File("C:\\Users\\luisitobonito\\Downloads\\img.jpg");
        MultipartFile multipartFile = new MockMultipartFile("img.jpg", new FileInputStream(file));


        //-- Creando: Params
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "username");


        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/usuarios/imagen-perfil/upload/")
                        .file("archivo", multipartFile.getBytes())
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+ Datos.tokenAdmin))
                    .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    @Order(2)
    @DisplayName("editarUrlImagenPerfilUsuario() - BatRequest")
    void editarUrlImagenPerfilUsuarioBadRequest() throws Exception {


        //-- Datos:
        Mockito.when(this.iServicesUsuarios.buscarUsuarioByUsername(Mockito.any())).thenReturn(Datos.usuario1);
        Mockito.doNothing().when(this.iServicesUsuarios).editarUrlImagenPerfilUsuario(Mockito.any(),Mockito.any());

        //-- Creando: Imagen (File)
        File file = new File("C:\\Users\\luisitobonito\\Downloads\\img.jpg");
        MultipartFile multipartFile = new MockMultipartFile("img.jpg", new FileInputStream(file));


        //-- Creando: Params
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("otro", "username");


        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/usuarios/imagen-perfil/upload/")
                        .file("archivo", multipartFile.getBytes())
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+ Datos.tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }


    //------------------------------------------------------------------------------------------------
    @Test
    @Order(3)
    @DisplayName("eliminarFotoPerfil() - Eliminada")
    void eliminarFotoPerfil() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesUsuarios.buscarUsuarioByUsername(Mockito.any())).thenReturn(Datos.usuario1);
        Mockito.doNothing().when(this.iServicesUsuarios).editarUrlImagenPerfilUsuario(Mockito.any(), Mockito.any());


        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/imagen-perfil/eliminar/1")
                .contentType(MediaType.APPLICATION_JSON)
                 .header("Authorization","Bearer "+ Datos.tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @Order(3)
    @DisplayName("eliminarFotoPerfil() - Usuario No Encontrado")
    void eliminarFotoPerfilUsuarioNoEncontrado() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesUsuarios.buscarUsuarioByUsername(Mockito.any())).thenReturn(null);
        Mockito.doNothing().when(this.iServicesUsuarios).editarUrlImagenPerfilUsuario(Mockito.any(), Mockito.any());


        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/imagen-perfil/eliminar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer "+ Datos.tokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());

    }



    //------------------------------------------------------------------------------------------------
    @Test
    @Order(4)
    @DisplayName("showImagenPerfil() - La muestra")
    void showImagenPerfil() {
    }
}