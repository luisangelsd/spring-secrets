package com.secrets.dao;

import com.secrets.dao.modelo.entitys.EntityCategoria;
import com.secrets.dao.modelo.entitys.EntitySecreto;
import com.secrets.dao.oauth2.services.entitys.EntityRol;
import com.secrets.dao.oauth2.services.entitys.EntityUsuario;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Datos {

    public static String tokenAdmin="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODg2NzQ2NTQsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiJmZjUyNTgwMy1hOTIyLTQyNWQtODBjNy1iYzUzMzllZTAzOGQiLCJjbGllbnRfaWQiOiJhbmd1bGFyYXBwIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl19.3uDd45Rjzas_vrNdEYcQYnTbGa2AGujZ9dqk2XUbA5A";
    public static String tokenUser="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODg2MDM4NjYsInVzZXJfbmFtZSI6InVzZXIiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiNjBhYjliNmItM2E4OS00ZGIzLTgxNmItMzViMmViZGM0YTdjIiwiY2xpZW50X2lkIjoiYW5ndWxhcmFwcCIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.nM1GUIvilOTji-S8UBHToJf2mp1fA0vwF05b6gqeXFM";


    public static EntityCategoria categoriaAmigos=new EntityCategoria(1L,"amigos");
    public static  EntityCategoria categoriaFamilia=new EntityCategoria(2L,"familia");
    public static EntityCategoria categoriaConocidos=new EntityCategoria(3L,"conocidos");

    public static EntitySecreto secreto1=new EntitySecreto(1L, "Este es el secreto no.1", LocalDate.now() , categoriaAmigos);
    public static EntitySecreto secreto2=new EntitySecreto(2L, "Este es el secreto no.2", LocalDate.now() , categoriaAmigos);
    public static  EntitySecreto secreto3=new EntitySecreto(3L, "Este es el secreto no.3", LocalDate.now() , categoriaAmigos);

    public static EntityRol roleAdmin=new EntityRol(1,"admin");
    public static EntityRol roleUser=new EntityRol(2,"user");

    public static EntityUsuario usuario1=new EntityUsuario(1,"user1","pass1", true, "Sin Descripcion","mi-perfil-1.png", getAllRoles());



    //------------ SECRETOS
    public static List<EntitySecreto> getListarSecretos(){
        List<EntitySecreto> list=new ArrayList<>();
        list.add(secreto1);
        list.add(secreto2);
        list.add(secreto3);
        return list;
    }

    public static Page<EntitySecreto> getListarSecretosPage(){
        Page<EntitySecreto> page=  Page.empty();
        List<EntitySecreto> list= new ArrayList<>();
        list.add(secreto1);
        list.add(secreto2);
        return page;
    }

    public static List<EntityCategoria> getListarCategorias(){
        List<EntityCategoria> list= new ArrayList<>();
        list.add(categoriaAmigos);
        list.add(categoriaFamilia);
        list.add(categoriaConocidos);

        return list;
    }

    public static  List<EntityRol> getAllRoles(){
         List<EntityRol> listRoles=new ArrayList<>();
        listRoles.add(roleAdmin);
        listRoles.add(roleUser);
        return listRoles;
    }


}
