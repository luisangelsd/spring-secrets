package com.secrets.dao.modelo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secrets.dao.modelo.entitys.EntityUsuario;



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


	@Override
	public void eliminarFotoPorUsername(String username) throws Exception {
		this.servicesCrud.eliminarImagenUsuario(username);
		
	}

}
