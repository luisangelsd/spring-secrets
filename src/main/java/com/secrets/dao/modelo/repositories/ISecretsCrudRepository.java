package com.secrets.dao.modelo.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.secrets.dao.modelo.entitys.EntitySecreto;

@Repository
public interface ISecretsCrudRepository extends JpaRepository<EntitySecreto, Long>{
	
	//-- Buscar por categoria
	@Query(value = "SELECT * FROM secretos u  WHERE u.categoria=:categoria", nativeQuery = true)
	public List<EntitySecreto> faindByCategory(@Param("categoria") String categoria);


}