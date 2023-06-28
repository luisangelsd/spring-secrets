package com.secrets.dao.modelo.servicios;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.secrets.dao.modelo.entitys.EntitySecreto;

public interface IServicesSecrets{
	
	
	public List<EntitySecreto> listarSecretos() throws DataAccessException;
	public Page<EntitySecreto> listarSecretosPaginado(Pageable pageable) throws DataAccessException;
	public List<EntitySecreto> listarSecretosByIdCategoria(Long idCategoria) throws DataAccessException;
	public EntitySecreto buscarSecretoById(Long id) throws DataAccessException;
	public EntitySecreto guardarEditarSecreto(EntitySecreto entitySecretos)throws DataAccessException;
	public void eliminarSecretoById(Long id) throws DataAccessException;
	
	

}
