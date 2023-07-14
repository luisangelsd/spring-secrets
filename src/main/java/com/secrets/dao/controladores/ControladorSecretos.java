package com.secrets.dao.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.secrets.dao.modelo.entitys.EntityCategoria;
import com.secrets.dao.modelo.entitys.EntitySecreto;
import com.secrets.dao.modelo.excepciones.RunTimeExceptionNotFound;
import com.secrets.dao.modelo.servicios.IServicesCategorias;
import com.secrets.dao.modelo.servicios.IServicesSecrets;

@CrossOrigin({"*"})
@RestController
@RequestMapping()
public class ControladorSecretos {


	//-- Variables globales
	Map<String, Object> response=new HashMap<>();
	List<EntitySecreto> listaSecretos=null;
	EntitySecreto entitySecreto=null;
	EntityCategoria entityCategoria;
	Page<EntitySecreto> pageListaSecretos=null;


	//-- Inyeccion de servicios
	@Autowired
	@Qualifier(value = "servicesSecrets")
	public IServicesSecrets servicesSecrets;


	@Autowired
	@Qualifier(value = "servicesCategorias")
	public IServicesCategorias servicesCategorias;

	//--------------------------------------------------------------------------


	@GetMapping("/listar")
	@ResponseStatus(HttpStatus.OK)
	public List<EntitySecreto> listarSecretos(){

		//-- Servicio
		this.listaSecretos= this.servicesSecrets.listarSecretos();

		//-- Response
		return this.listaSecretos;
	}


	//--------------------------------------------------------------------------

	@GetMapping("/listar/page/{page}/elements/{elements}")
	@ResponseStatus(HttpStatus.OK)
	public Page<EntitySecreto> listarSecretosPaginado(@PathVariable Integer page, @PathVariable Integer elements){

		//-- Servicio
		this.pageListaSecretos=this.servicesSecrets.listarSecretosPaginado(PageRequest.of(page,elements));

		//-- Response
		return this.pageListaSecretos;
	}


	//--------------------------------------------------------------------------


	@GetMapping("/listar/categoria/{idCategoria}")
	@ResponseStatus(HttpStatus.OK)
	public List<EntitySecreto> listarSecretosByIdCategoria(@PathVariable Long idCategoria){

		//-- Validar: Categoria
		this.entityCategoria = this.servicesCategorias.buscarCategoriaById(idCategoria);
		if (this.entityCategoria == null) {
			throw new RunTimeExceptionNotFound("¡No existe la Categoria!");
		}

		//-- Service:
		this.listaSecretos=this.servicesSecrets.listarSecretosByIdCategoria(idCategoria);


		//-- Response
		return this.listaSecretos;

	}

	//--------------------------------------------------------------------------


	@GetMapping("buscar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public EntitySecreto buscarSecretoById(@PathVariable Long id){

		//-- Service:
		this.entitySecreto=this.servicesSecrets.buscarSecretoById(id);

		//-- Valid: Que no este vacio
		if (this.entitySecreto == null) {
			throw new RunTimeExceptionNotFound("¡El secreto no existe!");
		}

		//-- Response
		return this.entitySecreto;
	}


	//--------------------------------------------------------------------------


	@PostMapping("/guardar/categoria-id/{idCategoria}")
	@ResponseStatus(HttpStatus.OK)
	public EntitySecreto guardarSecreto(@Valid @RequestBody EntitySecreto requestEntity, @PathVariable Long idCategoria) {


		//-- Validar: Categoria
		this.entityCategoria=this.servicesCategorias.buscarCategoriaById(idCategoria);
		if (this.entityCategoria == null) {
			throw new RunTimeExceptionNotFound("¡No existe la Categoria!");
		}else {
			requestEntity.setEntityCategoria(this.entityCategoria);
		}

		//-- Servicio
		requestEntity.setId(null);
		this.entitySecreto=this.servicesSecrets.guardarEditarSecreto(requestEntity);

		//-- Response
		return this.entitySecreto;

	}


	//--------------------------------------------------------------------------


	@PutMapping("/editar/{idSecreto}/categoria-id/{idCategoria}")
	@ResponseStatus(HttpStatus.OK)
	public EntitySecreto editarSecreto(@Valid @RequestBody EntitySecreto requestEntity, @PathVariable Long idSecreto, @PathVariable Long idCategoria){


		//-- Validar: Secreto
		EntitySecreto entitySecretoActualizar=this.servicesSecrets.buscarSecretoById(idSecreto);
		if (entitySecretoActualizar == null) {
			throw new RunTimeExceptionNotFound("El secreto no puede ser editado porque no existe");
		}

		//-- Validar: Categoria
		this.entityCategoria=this.servicesCategorias.buscarCategoriaById(idCategoria);
		if (this.entityCategoria == null) {
			throw new RunTimeExceptionNotFound("No existe la categoria");
		}


		//-- Validar: Solamente se pueda editar el secreto si pertenece a la misma fecha
		if (!entitySecretoActualizar.getfCreacion().equals( LocalDate.now())) {
			System.out.println("********************************************** Fecha hoy:"+LocalDate.now());
			System.out.println("********************************************** Fecha registro"+entitySecretoActualizar.getfCreacion());
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			System.out.println("**********************************************");
			throw new RuntimeException("Lo sentimos, este secreto no puede ser editado, debido a que pertenece a una fecha diferente a la de hoy");
		}

		//-- Servicio: Editar
		entitySecretoActualizar.setSecreto(requestEntity.getSecreto());
		entitySecretoActualizar.setEntityCategoria(this.entityCategoria);
		this.entitySecreto=this.servicesSecrets.guardarEditarSecreto(entitySecretoActualizar);
		return this.entitySecreto;

	}


	//--------------------------------------------------------------------------

	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PutMapping("adm/editar/{idSecreto}/categoria-id/{idCategoria}")
	@ResponseStatus(HttpStatus.OK)
	public EntitySecreto guardarEditarSecretoAdm(@Valid @RequestBody EntitySecreto requestEntity, @PathVariable Long idSecreto, @PathVariable Long idCategoria){


		//-- Validar: Secreto
		EntitySecreto entitySecretoActualizar=this.servicesSecrets.buscarSecretoById(idSecreto);
		if (entitySecretoActualizar == null) {
			throw new RunTimeExceptionNotFound("El secreto no puede ser editado porque no existe");
		}

		//-- Validar: Categoria
		this.entityCategoria=this.servicesCategorias.buscarCategoriaById(idCategoria);
		if (this.entityCategoria == null) {
			throw new RunTimeExceptionNotFound("No existe la categoria");
		}


		//-- Añadir Fecha
		if (requestEntity.getfCreacion() != null) {
			entitySecretoActualizar.setfCreacion(requestEntity.getfCreacion());
		}

		//-- Servicio: Editar
		entitySecretoActualizar.setSecreto(requestEntity.getSecreto());
		entitySecretoActualizar.setEntityCategoria(this.entityCategoria);
		this.entitySecreto=this.servicesSecrets.guardarEditarSecreto(entitySecretoActualizar);
		return this.entitySecreto;

	}


	//--------------------------------------------------------------------------


	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("adm/eliminar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public EntitySecreto eliminarSecretoById(@PathVariable Long id){


		//-- Servicio: Buscar Secreto
		this.entitySecreto=this.servicesSecrets.buscarSecretoById(id);
		if (this.entitySecreto == null) {
			throw new RunTimeExceptionNotFound("No puedes eliminar este secreto porque no existe");
		}

		//-- Servicio: Eliminar
		this.servicesSecrets.eliminarSecretoById(id);
		return this.entitySecreto;

	}


}


