package com.secrets.dao.modelo.servicios;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.secrets.dao.modelo.entitys.EntitySecretos;

public interface IServiceDaoSecrets{
	
	public List<EntitySecretos> listarTodos() throws DataAccessException;
	public List<EntitySecretos> listarPorCategoria(String categoria) throws DataAccessException;
	public EntitySecretos buscarPorId(Long id) throws DataAccessException;
	public EntitySecretos guardar(EntitySecretos entitySecretos)throws DataAccessException;
	public void eliminarPorId(Long id) throws DataAccessException;
	public Boolean existePorId(Long id)throws DataAccessException;
	
	public Page<EntitySecretos> paginado(Pageable pageable) throws DataAccessException;
	

}
