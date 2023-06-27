package com.secrets.dao.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.secrets.dao.modelo.entitys.EntitySecreto;
import com.secrets.dao.modelo.excepciones.RunTimeSecretNotFound;
import com.secrets.dao.modelo.servicios.IServicesSecrets;

@CrossOrigin({"*"})
@RestController
@RequestMapping()
public class ControladorSecretos {
	
	
	//-- Variables globales
	Map<String, Object> response=new HashMap<>();
	List<EntitySecreto> listaSecretos=null;
	EntitySecreto secreto=null;
	Page<EntitySecreto> pageListaSecretos=null;
	
	
	//-- Inyeccion de servicios
	@Autowired
	@Qualifier(value = "serviceDaoSecrets")
	public IServicesSecrets serviceDaoSecretos;
	
	//--------------------------------------------------------------------------
	
	// Method: List All
	@GetMapping("/listar")
	@ResponseStatus(HttpStatus.OK)
	public List<EntitySecreto> listar(){
	
			//-- Servicio: Obtener Data
			this.listaSecretos= this.serviceDaoSecretos.listarTodos();
			
			//-- Response: Enviar Lista vacia si no se encuentra registros
			if (this.listaSecretos == null) {
				this.listaSecretos=new ArrayList<>();
				return this.listaSecretos;
			}
		
			//-- Response: Enviar Registros
			return this.listaSecretos;					
	}
	
	//--------------------------------------------------------------------------
	
	// Method: List Page
	@GetMapping("/listar/page/{page}/elements/{elements}")
	@ResponseStatus(HttpStatus.OK)
	public Page<EntitySecreto> paginado(@PathVariable(name = "page", required = true) Integer page, @PathVariable(name = "elements", required = true) Integer elements){
			
		//-- Servicio: Obtener Data
			this.pageListaSecretos=this.serviceDaoSecretos.paginado(PageRequest.of(page,elements));	
			
		//-- Response: Enviar Respuesta
			return this.pageListaSecretos;					
	}
	

	//--------------------------------------------------------------------------
	
	// Method: List by Category
	@GetMapping("/listar/{categoria}")
	@ResponseStatus(HttpStatus.OK)
	public List<EntitySecreto> listarPorCategoria(@PathVariable(name = "categoria", required = true) String categoria){

			//-- Service:
			this.listaSecretos=this.serviceDaoSecretos.listarPorCategoria(categoria);
			
			//-- Response: Validar que no este vacio
			if ( this.listaSecretos == null) {
				this.listaSecretos=new ArrayList<>();
				return this.listaSecretos;
			}
			
			//-- Response: Enviar Lista 
			return this.listaSecretos;	
		
	}
	
	//--------------------------------------------------------------------------
	
	
	// Method Search by ID
	@GetMapping("buscar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public EntitySecreto buscarPorId(@PathVariable(name = "id", required = true)Long id){
		
		//-- Service:
			this.secreto=this.serviceDaoSecretos.buscarPorId(id);
			
		//-- Valid: Que no este vacio
		if (this.secreto == null) {
			throw new RunTimeSecretNotFound("¡El secreto no existe!");
		}
			
		//-- Response
		return this.secreto;
	}
	
	//--------------------------------------------------------------------------
	
	
	//Method Save 
	@PostMapping("/guardar")
	@ResponseStatus(HttpStatus.OK)
	public EntitySecreto guardar(@Valid @RequestBody EntitySecreto entitySecreto) {	
			
		//-- Servicio
			this.secreto=this.serviceDaoSecretos.guardar(entitySecreto);
		//-- Response
			return this.secreto;
			
	}
	
	
	//--------------------------------------------------------------------------
	
	
	/* Method Update
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public EntitySecreto editarPorId(@Valid @RequestBody EntitySecreto entitySecretos, @PathVariable( name = "id", required = true) Long id){
		
	
		//-- Servicio: Buscar Secreto
			EntitySecreto entitySecretoActualizar=this.serviceDaoSecretos.buscarPorId(id);
		
		//-- Validar: Que exista el secreto
			if (entitySecretoActualizar == null) {
				throw new RunTimeSecretNotFound("El secreto no puede ser editado porque no existe");
			}
			
			//-- Validar: Solamente se pueda editar el secreto si pertenece a la misma fecha
			if (!this.secreto.getfCreacion().equals( LocalDate.now())) {
				throw new RuntimeException("Lo sentimos, este secreto no puede ser eliminado, debido a que pertenece a una fecha diferente a la de hoy");
			}
			
			//-- Servicio: Editar
			 entitySecretoActualizar.setSecreto(entitySecretos.getSecreto());
			 entitySecretoActualizar.setCategoria(entitySecretos.getCategoria());
			 this.secreto=this.serviceDaoSecretos.guardar(entitySecretoActualizar);
			 return this.secreto;
	
	}*/
	
	
	//--------------------------------------------------------------------------
	
	
	/* Method Update whit Admin
	@Secured({"ROLE_ADMIN"})
	@PutMapping("adm/editar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public EntitySecreto editarPorIdWhitAdmin(@Valid @RequestBody EntitySecreto entitySecretos, @PathVariable( name = "id", required = true) Long id){
		
			//-- Servicio: Buscar por id
			EntitySecreto entitySecretoActualizar=this.serviceDaoSecretos.buscarPorId(id);
			
			//-- Validar: Que exista 
			if (entitySecretoActualizar == null) {
				throw new RunTimeSecretNotFound("El secreto no puede ser editado porque no existe");
			}
			
			
			//-- Servicio: Actualizar
			 entitySecretoActualizar.setSecreto(entitySecretos.getSecreto());
			 entitySecretoActualizar.setCategoria(entitySecretos.getCategoria());
			 if (entitySecretos.getfCreacion()!=null) { //-- Si el Post contiene una fecha la agregamos, si no dejamos la que esta por defecto
				entitySecretoActualizar.setfCreacion(entitySecretos.getfCreacion());
			}
			
			 //-- Response
			 this.secreto = this.serviceDaoSecretos.guardar(entitySecretoActualizar);
			 return this.secreto;
	
	}*/
	
	//--------------------------------------------------------------------------

	
	//Method Delate
	@DeleteMapping("eliminar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public EntitySecreto eliminarPorId(@PathVariable(name = "id", required = true) Long id){
		
			//-- Servicio: Buscar Secreto
			this.secreto=this.serviceDaoSecretos.buscarPorId(id);
			
			//-- Validar: Que exista
			if (this.secreto==null) {
				throw new RunTimeSecretNotFound("No puedes eliminar este secreto porque no existe");
			}
			
			//-- Validar que solamente se pueda eliminar el secreto si pertenece a la misma fecha
			if (!this.secreto.getfCreacion().equals( LocalDate.now())) {		
				throw new RuntimeException("Lo sentimos, este secreto no puede ser eliminado, debido a que pertenece a una fecha diferente a la de hoy");
			}
			
			this.serviceDaoSecretos.eliminarPorId(id);
			return this.secreto;

		
	}
	
	
	//--------------------------------------------------------------------------
	
	
	//Method Delate como admin
		@Secured({"ROLE_ADMIN"})
		@DeleteMapping("adm/eliminar/{id}")
		@ResponseStatus(HttpStatus.OK)
		public EntitySecreto eliminarPorIdWhirAdmin(@PathVariable(name = "id", required = true) Long id){
			

			//-- Servicio: Buscar Secreto
			this.secreto=this.serviceDaoSecretos.buscarPorId(id);
				
			//-- Validar: Que exista
			if (this.secreto == null) {
					throw new RunTimeSecretNotFound("No puedes eliminar este secreto porque no existe");
			}
				
			//-- Servicio: Eliminar
			this.serviceDaoSecretos.eliminarPorId(id);
			return this.secreto;

		}
	
	
}


