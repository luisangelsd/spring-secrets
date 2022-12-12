package com.secrets.dao.modelo.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secrets.dao.modelo.dao.IDaoSecretosCrudRepository;
import com.secrets.dao.modelo.entitys.EntitySecretos;

@Service(value = "servicioDaoSecretos")
public class ServicioDaoSecretos implements IServicioDaoSecretos{
	
	
	@Autowired
	private IDaoSecretosCrudRepository iDaoSecretosCrudRepository;

	
	
	
	@Override
	public List<EntitySecretos> listarTodos() {
		
		return (List<EntitySecretos>) iDaoSecretosCrudRepository.findAll();
	}

	@Override
	public List<EntitySecretos> listarPorCategoria(String categoria) {	
		return iDaoSecretosCrudRepository.faindByCategory(categoria);
	}

	
	@Override
	public EntitySecretos buscarPorId(Long id) {
		return iDaoSecretosCrudRepository.findById(id).orElse(null);
	}
	

	@Override
	public EntitySecretos guardar(EntitySecretos entitySecretos) {	
		return iDaoSecretosCrudRepository.save(entitySecretos);
	}

	@Override
	public void eliminarPorId(Long id) {
		 iDaoSecretosCrudRepository.deleteById(id);;
	}

	@Override
	public Boolean existePorId(Long id) {		
		return iDaoSecretosCrudRepository.existsById(id);
	}

}
