package com.secrets.dao.oauth2.services;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.secrets.dao.oauth2.services.entitys.EntityUsuario;

public interface IServiceCrudRepositoryUsuarios extends CrudRepository<EntityUsuario, Integer> {

	//=== Este metodo es el de aout2
	@Transactional
	@Query(value = "SELECT * FROM USUARIOS WHERE USERNAME= :username", nativeQuery = true)
	public EntityUsuario findUsuarioByUsername(@Param("username") String username);
	

	//---  Metodos: Imagen de perfil
	@Modifying
	@Transactional
	@Query(value = "UPDATE usuarios SET url_foto = :urlImagen WHERE username= :username", nativeQuery = true)
	public void editarImagenUsuario(@Param(value = "username")String username, @Param(value = "urlImagen") String urlImagen)throws Exception;
	

	

}
