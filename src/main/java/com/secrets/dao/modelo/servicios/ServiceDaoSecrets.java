package com.secrets.dao.modelo.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secrets.dao.modelo.entitys.EntitySecretos;

@Service(value = "serviceDaoSecrets")
public class ServiceDaoSecrets implements IServiceDaoSecrets{
	
	
	@Autowired
	private IServiceCrudRepository serviceCrudRepository;

	
	
	
	@Override
	public List<EntitySecretos> listarTodos(){
		return (List<EntitySecretos>) serviceCrudRepository.findAll();
	}

	@Override
	public List<EntitySecretos> listarPorCategoria(String categoria) {	
		return serviceCrudRepository.faindByCategory(categoria);
	}

	
	@Override
	public EntitySecretos buscarPorId(Long id) {
		return serviceCrudRepository.findById(id).orElse(null);
	}
	

	@Override
	public EntitySecretos guardar(EntitySecretos entitySecretos) {	
		return serviceCrudRepository.save(entitySecretos);
	}

	@Override
	public void eliminarPorId(Long id) {
		serviceCrudRepository.deleteById(id);
	}

	@Override
	public Boolean existePorId(Long id) {		
		return serviceCrudRepository.existsById(id);
	}

	
	//-- Metodo: Paginado de todos los registros
	@Override
	@Transactional(readOnly = true)
	public Page<EntitySecretos> paginado(Pageable pageable) {
		return this.serviceCrudRepository.findAll(pageable);
	}


}
