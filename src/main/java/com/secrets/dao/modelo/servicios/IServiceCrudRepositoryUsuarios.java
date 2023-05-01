package com.secrets.dao.modelo.servicios;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.secrets.dao.oauth2.entitys.EntityUsuario;

public interface IServiceCrudRepositoryUsuarios extends CrudRepository<EntityUsuario, Integer> {

	//=== Este metodo es el de aout2
	@Query(value = "SELECT * FROM USUARIOS WHERE USERNAME= :username", nativeQuery = true)
	public EntityUsuario findUsuarioByUsername(@Param("username") String username);
}
