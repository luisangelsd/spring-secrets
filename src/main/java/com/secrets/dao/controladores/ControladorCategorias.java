package com.secrets.dao.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.secrets.dao.modelo.entitys.EntityCategoria;
import com.secrets.dao.modelo.servicios.IServicesCategorias;

@CrossOrigin({"*"})
@RestController
@RequestMapping("categorias/")
public class ControladorCategorias {
	
	//--- Variables Globales
	public EntityCategoria entityCategoria;
	public List<EntityCategoria> listEntityCategoria;
	
	
	//-- Inyeccion de servicios
	@Autowired
	@Qualifier(value = "servicesCategorias")
	public IServicesCategorias servicesCategorias;
	

	//----------------------------------------------------
	@GetMapping("listar/")
	@ResponseStatus(HttpStatus.OK)
	public List<EntityCategoria> listarCategorias(){
		
		//-- Servicio: Obtener Data
		this.listEntityCategoria= this.servicesCategorias.listarCategorias();
		
		//-- Response: Enviar Registros
		return this.listEntityCategoria;			
	}
	


}
