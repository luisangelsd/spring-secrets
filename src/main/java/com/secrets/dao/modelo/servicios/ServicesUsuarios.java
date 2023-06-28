package com.secrets.dao.modelo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secrets.dao.modelo.repositories.IServiceCrudRepositoryUsuarios;
import com.secrets.dao.oauth2.services.entitys.EntityUsuario;


@Service("servicesUsuarios")
public class ServicesUsuarios implements IServicesUsuarios{

	
	//-- Inyeccion de servicios
	@Autowired
	private IServiceCrudRepositoryUsuarios servicesCrud;
	
	
	
	
	
	// =============== Metodos ===============
	

	@Override
	public EntityUsuario buscarUserPorUsername(String username) throws Exception {
		return this.servicesCrud.findUsuarioByUsername(username);
	}


	@Override
	public void editarFotoPorUsername(String username, String urlFoto) throws Exception {
			this.servicesCrud.editarImagenUsuario(username, urlFoto);
		
	}



}
