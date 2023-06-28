package com.secrets.dao.modelo.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.secrets.dao.modelo.entitys.EntitySecreto;

@Repository
public interface ISecretsCrudRepository extends JpaRepository<EntitySecreto, Long>{
	
	
	@Query(value = "SELECT * FROM secretos WHERE id_categoria= :id", nativeQuery = true)
	public List<EntitySecreto> listarSecretosByIdCategoria(@Param("id") Long id);


}
