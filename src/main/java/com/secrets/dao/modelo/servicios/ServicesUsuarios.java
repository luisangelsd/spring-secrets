package com.secrets.dao.modelo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secrets.dao.modelo.repositories.IUsuariosCrudRepository;
import com.secrets.dao.oauth2.services.entitys.EntityUsuario;


@Service("servicesUsuarios")
public class ServicesUsuarios implements IServicesUsuarios{

	
	//-- Inyeccion de servicios
	@Autowired
	private IUsuariosCrudRepository usuariosCrudRepository;
	
	
	
	
	
	//--------------------------METODOS----------------------------------
	

	@Override
	public EntityUsuario buscarUsuarioByUsername(String username){
		return this.usuariosCrudRepository.buscarUsuarioByUsername(username);
	}


	@Override
	public void editarUrlImagenPerfilUsuario(String username, String urlFoto){
			this.usuariosCrudRepository.editarUrlImagenPerfilUsuario(username, urlFoto);
		
	}



}
