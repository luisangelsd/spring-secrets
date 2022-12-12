package com.secrets.dao.modelo.servicios;

import java.util.List;

import com.secrets.dao.modelo.entitys.EntitySecretos;

public interface IServicioDaoSecretos{
	
	public List<EntitySecretos> listarTodos();
	public List<EntitySecretos> listarPorCategoria(String categoria);
	public EntitySecretos buscarPorId(Long id);
	public EntitySecretos guardar(EntitySecretos entitySecretos);
	public void eliminarPorId(Long id);
	public Boolean existePorId(Long id);

	

}
