package com.secrets.dao.modelo.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secrets.dao.modelo.entitys.EntitySecreto;
import com.secrets.dao.modelo.repositories.ISecretsCrudRepository;

@Service(value = "servicesSecrets")
public class ServicesSecrets implements IServicesSecrets{
	
	
	//-- Inyección de Repository
	@Autowired
	private ISecretsCrudRepository serviceCrudRepository;

	
	//--------------------------METODOS----------------------------------

	@Override
	public List<EntitySecreto> listarSecretos(){
		List<EntitySecreto> list=this.serviceCrudRepository.findAll();
		return (list==null)?list=new ArrayList<>():list;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<EntitySecreto> listarSecretosPaginado(Pageable pageable) {
		return this.serviceCrudRepository.findAll(pageable);
	}


	@Override
	public List<EntitySecreto> listarSecretosByIdCategoria(Long idCategoria) {
		List<EntitySecreto> list=this.serviceCrudRepository.listarSecretosByIdCategoria(idCategoria);
		return (list==null)?list=new ArrayList<>():list;
	}

	
	@Override
	public EntitySecreto buscarSecretoById(Long id) {
		return serviceCrudRepository.findById(id).orElse(null);
	}
	

	@Override
	public EntitySecreto guardarEditarSecreto(EntitySecreto entitySecretos) {	
		return serviceCrudRepository.save(entitySecretos);
	}

	
	@Override
	public void eliminarSecretoById(Long id) {
		serviceCrudRepository.deleteById(id);
	}




}
