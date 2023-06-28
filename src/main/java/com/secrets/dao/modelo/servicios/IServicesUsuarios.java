package com.secrets.dao.modelo.servicios;

import org.springframework.dao.DataAccessException;

import com.secrets.dao.oauth2.services.entitys.EntityUsuario;

public interface IServicesUsuarios {
	

	public EntityUsuario buscarUsuarioByUsername(String username) throws DataAccessException;
	public void editarUrlImagenPerfilUsuario(String username, String urlFoto) throws DataAccessException;
	


}
