package com.secrets.dao.modelo.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.secrets.dao.oauth2.services.entitys.EntityUsuario;

public interface IUsuariosCrudRepository extends CrudRepository<EntityUsuario, Integer> {

	
	@Transactional
	@Query(value = "SELECT * FROM USUARIOS WHERE USERNAME = :username", nativeQuery = true)
	public EntityUsuario buscarUsuarioByUsername(@Param("username") String username);
	

	
	@Modifying
	@Transactional
	@Query(value = "UPDATE usuarios SET url_foto = :urlImagen WHERE username= :username", nativeQuery = true)
	public void editarUrlImagenPerfilUsuario(@Param(value = "username")String username, @Param(value = "urlImagen") String urlImagen);
	

	

}
