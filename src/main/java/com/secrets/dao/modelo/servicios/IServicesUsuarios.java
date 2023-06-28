package com.secrets.dao.modelo.servicios;

import com.secrets.dao.oauth2.services.entitys.EntityUsuario;

public interface IServicesUsuarios {
	

	public EntityUsuario buscarUsuarioByUsername(String username) throws Exception;
	public void editarUrlImagenPerfilUsuario(String username, String urlFoto)throws Exception;
	


}
