package com.secrets.dao.modelo.servicios;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.secrets.dao.modelo.entitys.EntitySecreto;

public interface IServicesSecrets{
	
	//-- Metodos: CRUD
	public List<EntitySecreto> listarTodos() throws DataAccessException;
	public List<EntitySecreto> listarPorCategoria(String categoria) throws DataAccessException;
	public EntitySecreto buscarPorId(Long id) throws DataAccessException;
	public EntitySecreto guardar(EntitySecreto entitySecretos)throws DataAccessException;
	public void eliminarPorId(Long id) throws DataAccessException;
	public Boolean existePorId(Long id)throws DataAccessException;
	
	//-- Metodos: Paginado
	public Page<EntitySecreto> paginado(Pageable pageable) throws DataAccessException;
	

}
