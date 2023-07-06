package com.secrets.dao.controladores;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.secrets.dao.Datos;
import com.secrets.dao.modelo.entitys.EntitySecreto;
import com.secrets.dao.modelo.servicios.IServicesCategorias;
import com.secrets.dao.modelo.servicios.IServicesSecrets;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//-- Test: Controllers
//-- Probamos los endpoints directamente sin pasar por el Services
//-- Aquí simularemos nuestro service con datos de prueba y solo validaremos el controller con su response y request :)

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControladorSecretosTest_MockitoMVC {

    //-- Inyección
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServicesSecrets iServicesSecrets;

    @MockBean
    private IServicesCategorias iServicesCategorias;


    //---

    ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void beforeEach() {
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    //------------------------------------------------------------------------------------------------
    @Test
    @Order(1)
    @DisplayName("listarSecretos() - Encuentra Registros")
    void listarSecretos() throws Exception {
        //-- Datos
        Mockito.when(this.iServicesSecrets.listarSecretos()).thenReturn(Datos.getListarSecretos());

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/listar")  //-- Request
                .contentType(MediaType.APPLICATION_JSON))                    //-- Request
                .andExpect(MockMvcResultMatchers.status().isOk())            //-- Response
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))                              //-- Response
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))                             //-- Response
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].secreto").value("Este es el secreto no.1")) //-- Response
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].entityCategoria.nombre").value("amigos"));  //-- Response
    }

    @Test
    @Order(1)
    @DisplayName("listarSecretos() - No Encuentra Registros")
    void listarSecretosNoEncuentra() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesSecrets.listarSecretos()).thenReturn( new ArrayList<>() );

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/listar")  //-- Request
                        .contentType(MediaType.APPLICATION_JSON))             //-- Request
                .andExpect(MockMvcResultMatchers.status().isOk())            //-- Response
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    //------------------------------------------------------------------------------------------------
    @Test //-- Pendiente: Problemas con el test Page
    @Order(2)
    @DisplayName("listarSecretosPaginado()- Encuentra datos")
    void listarSecretosPaginado() throws Exception {
//        //-- Datos
//        Mockito.when(this.iServicesSecrets.listarSecretosPaginado( PageRequest.of(1,2))).thenReturn( Datos.getListarSecretosPage() );
//
//        //-- Servicio + Test
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/listar/page/1/elements/2")
//                 .contentType(MediaType.APPLICATION_JSON))                   //-- Request: Tipo de datos enviados en request body
//                .andExpect(MockMvcResultMatchers.status().isOk())            //-- Response: Tipo de status que espera recibir
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));  //-- Response: Tipo de response body
        }



    //------------------------------------------------------------------------------------------------
    @Test
    @Order(3)
    @DisplayName("listarSecretosByIdCategoria() - Encuntra registros")
    void listarSecretosByIdCategoria() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesCategorias.buscarCategoriaById(Mockito.any())).thenReturn(Datos.categoriaAmigos);
        Mockito.when(this.iServicesSecrets.listarSecretosByIdCategoria(Mockito.any())).thenReturn(Datos.getListarSecretos());

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/listar/categoria/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)) //-- Request ;
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].secreto").value("Este es el secreto no.1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].entityCategoria.nombre").value("amigos"));
    }

    @Test
    @Order(3)
    @DisplayName("listarSecretosByIdCategoria() - No Encuentra registros")
    void listarSecretosByIdCategoriaNoEncontro() throws Exception {

        //-- Datos

        Mockito.when(this.iServicesCategorias.buscarCategoriaById(Mockito.any())).thenReturn(Datos.categoriaAmigos);
        Mockito.when(this.iServicesSecrets.listarSecretosByIdCategoria(Mockito.any())).thenReturn(new ArrayList<>());


        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/listar/categoria/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)); //-- Request
    }

    @Test
    @Order(3)
    @DisplayName("listarSecretosByIdCategoria() - No existe categoria")
    void listarSecretosByIdCategoriaNoExiste() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesCategorias.buscarCategoriaById(Mockito.any())).thenReturn(null);
        Mockito.when(this.iServicesSecrets.listarSecretosByIdCategoria(Mockito.any())).thenReturn(new ArrayList<>());


        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/listar/categoria/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)); //-- Request
    }



    //------------------------------------------------------------------------------------------------

    @Test
    @Order(4)
    @DisplayName("buscarSecretoById() - Encuentra Secreto")
    void buscarSecretoById() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesSecrets.buscarSecretoById(Mockito.any())).thenReturn(Datos.secreto1);

        //-- Servicios + Test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/buscar/1") //-- Request
                .contentType(MediaType.APPLICATION_JSON)) //-- Request
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.secreto").value("Este es el secreto no.1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.entityCategoria.nombre").value("amigos"));
    }

    @Test
    @Order(4)
    @DisplayName("buscarSecretoById() - No Encuentra Secreto")
    void buscarSecretoByIdNoEncuentra() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesSecrets.buscarSecretoById(Mockito.any())).thenReturn(null);

        //-- Servicios + Test
        this.mockMvc.perform(MockMvcRequestBuilders.get("/buscar/1")   //-- Request
                .contentType(MediaType.APPLICATION_JSON))                       //-- Request
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())         //-- Response
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)); //-- Response
    }



    //------------------------------------------------------------------------------------------------
    @Test
    @Order(5)
    @DisplayName("guardarEditarSecreto() - Guardado")
    void guardarEditarSecreto() throws Exception {



        //-- Datos

        Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(Datos.categoriaAmigos);
        Mockito.when(this.iServicesSecrets.guardarEditarSecreto(Mockito.any(EntitySecreto.class))).thenReturn( Datos.secreto1 );



        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.post("/guardar/categoria-id/1")
                .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(Datos.secreto1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.secreto").value("Este es el secreto no.1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.entityCategoria.nombre").value("amigos"));
    }

    @Test
    @Order(5)
    @DisplayName("guardarEditarSecreto() - Categoria no Encontrada")
    void guardarEditarSecretoCategoriaNoEncontrada() throws Exception {

        //-- Datos

        Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(null);
       // Mockito.when(this.iServicesSecrets.guardarEditarSecreto(Mockito.any(EntitySecreto.class))).thenReturn( Datos.secreto1 );


        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.post("/guardar/categoria-id/1")
                .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(Datos.secreto1)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(5)
    @DisplayName("guardarEditarSecreto() -Bad Request")
    void guardarEditarSecretoCategoriaBadRequest() throws Exception {

        //-- Datos
        // Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(null);
       // Mockito.when(this.iServicesSecrets.guardarEditarSecreto(Mockito.any(EntitySecreto.class))).thenReturn( Datos.secreto1 );
        EntitySecreto entitySecreto=new EntitySecreto();

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.post("/guardar/categoria-id/1")
                .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(entitySecreto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }



    //------------------------------------------------------------------------------------------------
    @Test
    @Order(6)
    @DisplayName("editarSecreto() - Con exito")
    void editarSecreto() throws Exception {

        //-- Datos

        Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(Datos.categoriaAmigos);
        Mockito.when(this.iServicesSecrets.buscarSecretoById(1L)).thenReturn(Datos.secreto1);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.put("/editar/1/categoria-id/1")
                .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(Datos.secreto1)))
                .andExpect(MockMvcResultMatchers.status().isOk());
                //.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));


    }


    @Test
    @Order(6)
    @DisplayName("editarSecreto() - No Existe Categoria")
    void editarSecretoNoExisteCategoria() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(null);
        Mockito.when(this.iServicesSecrets.buscarSecretoById(1L)).thenReturn(Datos.secreto1);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.put("/editar/1/categoria-id/1")
                .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(Datos.secreto1)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    @Order(6)
    @DisplayName("editarSecreto() - No Existe Secreto")
    void editarSecretoNoExisteSecreto() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(Datos.categoriaAmigos);
        Mockito.when(this.iServicesSecrets.buscarSecretoById(1L)).thenReturn(null);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.put("/editar/1/categoria-id/1")
                .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(Datos.secreto1)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(6)
    @DisplayName("editarSecreto() - Fecha no valida")
    void editarSecretoFechaNoValida() throws Exception {

        //-- Datos
        EntitySecreto entitySecreto=Datos.secreto1;
        entitySecreto.setfCreacion((LocalDate.of(2023,05,01)));

        Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(Datos.categoriaAmigos);
        Mockito.when(this.iServicesSecrets.buscarSecretoById(1L)).thenReturn(Datos.secreto1);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.put("/editar/1/categoria-id/1")
                        .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString( entitySecreto )))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }



    @Test
    @Order(6)
    @DisplayName("editarSecreto() - BadRequest")
    void editarSecretoBadRequest() throws Exception {

        //-- Datos
        EntitySecreto entitySecreto=new EntitySecreto();
        //Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(Datos.categoriaAmigos);
        //Mockito.when(this.iServicesSecrets.buscarSecretoById(1L)).thenReturn(Datos.secreto1);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.put("/editar/1/categoria-id/1")
                        .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(entitySecreto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    //-------------------------------------------------------------------------------0-----------------

    @Test
    @Order(7)
    @DisplayName("editarSecretoAdm() - Con exito")
    void editarSecretoAdm() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(Datos.categoriaAmigos);
        Mockito.when(this.iServicesSecrets.buscarSecretoById(1L)).thenReturn(Datos.secreto1);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.put("/adm/editar/1/categoria-id/1")
                 .header("Authorization", "Bearer " + Datos.tokenAdmin)
                 .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(Datos.secreto1)))
                 .andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    @Order(7)
    @DisplayName("editarSecretoAdm() - Categoria No Existe")
    void editarSecretoAdmCategoriaNoExiste() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(null);
        Mockito.when(this.iServicesSecrets.buscarSecretoById(1L)).thenReturn(Datos.secreto1);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.put("/adm/editar/1/categoria-id/1")
                 .header("Authorization", "Bearer " + Datos.tokenAdmin)
                .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(Datos.secreto1)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }


    @Test
    @Order(7)
    @DisplayName("editarSecretoAdm() - Secreto no existe")
    void editarSecretoAdmSecretoNoExiste() throws Exception {

        //-- Datos
        Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(Datos.categoriaAmigos);
        Mockito.when(this.iServicesSecrets.buscarSecretoById(1L)).thenReturn(null);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.put("/adm/editar/1/categoria-id/1")
                        .header("Authorization", "Bearer " + Datos.tokenAdmin)
                        .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(Datos.secreto1)))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    @Order(7)
    @DisplayName("editarSecretoAdm() - BadRequest")
    void editarSecretoAdmBadRequest() throws Exception {

        //-- Datos
        EntitySecreto entitySecreto=new EntitySecreto();
        //Mockito.when(this.iServicesCategorias.buscarCategoriaById(1L)).thenReturn(Datos.categoriaAmigos);
        //Mockito.when(this.iServicesSecrets.buscarSecretoById(1L)).thenReturn(null);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.put("/adm/editar/1/categoria-id/1")
                 .header("Authorization", "Bearer " + Datos.tokenAdmin)
                .contentType(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(entitySecreto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }





    //------------------------------------------------------------------------------------------------
    @Test
    @Order(8)
    @DisplayName("eliminarSecretoById() - Funciona OK " )
    void eliminarSecretoById() throws Exception {

        //--Datos
        Mockito.when(this.iServicesSecrets.buscarSecretoById(Mockito.any())).thenReturn(Datos.secreto1);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/adm/eliminar/1")
                .header("Authorization", "Bearer " + Datos.tokenAdmin)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @Order(8)
    @DisplayName("eliminarSecretoById() - No existe secreto " )
    void eliminarSecretoByIdNoExisteSecreto() throws Exception {

        //--Datos
        Mockito.when(this.iServicesSecrets.buscarSecretoById(Mockito.any())).thenReturn(null);

        //-- Servicio + Test
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/adm/eliminar/1")
                .header("Authorization", "Bearer " + Datos.tokenAdmin)
                .contentType(MediaType.APPLICATION_JSON))
             .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

}