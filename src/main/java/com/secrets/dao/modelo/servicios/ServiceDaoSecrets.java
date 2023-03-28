package com.secrets.dao.modelo.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secrets.dao.modelo.dao.IServiceCrudRepository;
import com.secrets.dao.modelo.entitys.EntitySecretos;

@Service(value = "seerviceDaoSecrets")
public class ServiceDaoSecrets implements IServiceDaoSecrets{
	
	
	@Autowired
	private IServiceCrudRepository serviceCrudRepository;

	
	
	
	@Override
	public List<EntitySecretos> listarTodos() throws DataAccessException {
		
		return (List<EntitySecretos>) serviceCrudRepository.findAll() ;
	}

	@Override
	public List<EntitySecretos> listarPorCategoria(String categoria) throws DataAccessException {	
		return serviceCrudRepository.faindByCategory(categoria);
	}

	
	@Override
	public EntitySecretos buscarPorId(Long id) throws DataAccessException {
		return serviceCrudRepository.findById(id).orElse(null);
	}
	

	@Override
	public EntitySecretos guardar(EntitySecretos entitySecretos) throws DataAccessException {	
		return serviceCrudRepository.save(entitySecretos);
	}

	@Override
	public void eliminarPorId(Long id) throws DataAccessException {
		serviceCrudRepository.deleteById(id);;
	}

	@Override
	public Boolean existePorId(Long id) throws DataAccessException {		
		return serviceCrudRepository.existsById(id);
	}

	
	//-- Metodo: Paginado de todos los registros
	@Override
	@Transactional(readOnly = true)
	public Page<EntitySecretos> paginado(Pageable pageable) throws DataAccessException {
		return this.serviceCrudRepository.findAll(pageable);
	}


}
