package com.secrets.dao.modelo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import com.secrets.dao.modelo.entitys.EntitySecretos;


public interface IDaoSecretosCrudRepository extends CrudRepository<EntitySecretos, Long>{
	
	@Query(value = "SELECT * FROM secretos u  WHERE u.categoria=:categoria", nativeQuery = true)
	public List<EntitySecretos> faindByCategory(@Param("categoria") String categoria);
	

}
