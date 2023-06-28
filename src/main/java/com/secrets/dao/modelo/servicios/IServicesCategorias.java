package com.secrets.dao.modelo.servicios;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.secrets.dao.modelo.entitys.EntityCategoria;

public interface IServicesCategorias {
	
	
	public List<EntityCategoria> listarCategorias() throws DataAccessException;
	public EntityCategoria buscarCategoriaById(Long id) throws DataAccessException;

}
