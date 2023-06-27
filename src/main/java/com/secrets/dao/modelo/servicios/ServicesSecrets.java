package com.secrets.dao.modelo.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secrets.dao.modelo.entitys.EntitySecreto;

@Service(value = "serviceDaoSecrets")
public class ServicesSecrets implements IServicesSecrets{
	
	
	//-- Inyección de Repository
	@Autowired
	private ISecretsCrudRepository serviceCrudRepository;

	
	
	//--- Metodos
	@Override
	public List<EntitySecreto> listarTodos(){
		return (List<EntitySecreto>) serviceCrudRepository.findAll();
	}

	@Override
	public List<EntitySecreto> listarPorCategoria(String categoria) {	
		return serviceCrudRepository.faindByCategory(categoria);
	}

	
	@Override
	public EntitySecreto buscarPorId(Long id) {
		return serviceCrudRepository.findById(id).orElse(null);
	}
	

	@Override
	public EntitySecreto guardar(EntitySecreto entitySecretos) {	
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
	public Page<EntitySecreto> paginado(Pageable pageable) {
		return this.serviceCrudRepository.findAll(pageable);
	}


}
