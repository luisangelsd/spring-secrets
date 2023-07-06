package com.secrets.dao;

import com.secrets.dao.modelo.entitys.EntityCategoria;
import com.secrets.dao.modelo.entitys.EntitySecreto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Datos {

    public static EntityCategoria categoriaAmigos=new EntityCategoria(1L,"amigos");
    public static  EntityCategoria categoriaFamilia=new EntityCategoria(2L,"familia");
    public static EntityCategoria categoriaConocidos=new EntityCategoria(3L,"conocidos");

    public static EntitySecreto secreto1=new EntitySecreto(1L, "Este es el secreto no.1", LocalDate.now() , categoriaAmigos);
    public static EntitySecreto secreto2=new EntitySecreto(2L, "Este es el secreto no.2", LocalDate.now() , categoriaAmigos);
    public static  EntitySecreto secreto3=new EntitySecreto(3L, "Este es el secreto no.3", LocalDate.now() , categoriaAmigos);


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


}
