package com.secrets.dao.modelo.servicios;

import com.secrets.dao.modelo.entitys.EntityUsuario;

public interface IServicesUsuarios {
	

	//-- Metodos User
	public EntityUsuario buscarUserPorUsername(String username) throws Exception;
	
	
	//-- Metodos urlImagen para User
	public void editarFotoPorUsername(String username, String urlFoto)throws Exception;
	


}
