package com.secrets.dao.modelo.servicios;

import com.secrets.dao.oauth2.services.entitys.EntityUsuario;

public interface IServicesUsuarios {
	

	//-- Metodos User
	public EntityUsuario buscarUserPorUsername(String username) throws Exception;
	
	
	//-- Metodos urlImagen para User
	public void editarFotoPorUsername(String username, String urlFoto)throws Exception;
	


}
