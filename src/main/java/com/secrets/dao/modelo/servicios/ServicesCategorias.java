package com.secrets.dao.modelo.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secrets.dao.modelo.entitys.EntityCategoria;
import com.secrets.dao.modelo.repositories.ICategoriasCrudRepository;

@Service("servicesCategorias")
public class ServicesCategorias implements IServicesCategorias{

	//-- Inyección de Repository
	@Autowired
	private ICategoriasCrudRepository serviceRepositoryCategorias;
	
	
	//--------------------------METODOS----------------------------------
	@Override
	public EntityCategoria buscarCategoriaById(Long id) {
		return this.serviceRepositoryCategorias.findById(id).orElse(null);
	}


	@Override
	public List<EntityCategoria> listarCategorias(){
		List<EntityCategoria> list= (List<EntityCategoria>) this.serviceRepositoryCategorias.findAll();
		return (list == null)? list=new ArrayList<>() : list;
	}

}
