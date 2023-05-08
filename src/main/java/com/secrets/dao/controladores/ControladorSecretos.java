package com.secrets.dao.controladores;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.secrets.dao.modelo.entitys.EntitySecretos;
import com.secrets.dao.modelo.servicios.IServiceDaoSecrets;

@CrossOrigin({"*"})
@RestController
@RequestMapping()
public class ControladorSecretos {
	
	
	//-- Variables globales
	Map<String, Object> response=new HashMap<>();
	List<EntitySecretos> listaSecretos=null;
	EntitySecretos secreto=null;
	Page<EntitySecretos> pageListaSecretos=null;
	
	
	//-- Inyeccion de servicios
	@Autowired
	@Qualifier(value = "seerviceDaoSecrets")
	public IServiceDaoSecrets serviceDaoSecretos;
	
	
	// Method List
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		
		
		try {
			
			this.listaSecretos=this.serviceDaoSecretos.listarTodos();
			
			if (this.listaSecretos.isEmpty() || this.listaSecretos ==null) {
				this.response.put("error", "No se han encontrado registros");
				return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<List<EntitySecretos>>(this.listaSecretos,HttpStatus.OK);			
		}
		catch (DataAccessException e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}//end
	
	 
	// Method List page
	@GetMapping("/listar/page/{page}/elements/{elements}")
	public ResponseEntity<?> paginado(@PathVariable(name = "page", required = true) Integer page, @PathVariable(name = "elements", required = true) Integer elements){
		
		
		try {
			
			this.pageListaSecretos=this.serviceDaoSecretos.paginado(PageRequest.of(page,elements));	
			return new ResponseEntity<Page<EntitySecretos>>(this.pageListaSecretos,HttpStatus.OK);			
		}
		catch (DataAccessException e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}//end
	
	
	// Method List by Category
	@GetMapping("/listar/{categoria}")
	public ResponseEntity<?> listarPorCategoria(@PathVariable(name = "categoria", required = true) String categoria){
		
		try {
			this.listaSecretos=this.serviceDaoSecretos.listarPorCategoria(categoria);
			
			if (this.listaSecretos.isEmpty() || this.listaSecretos ==null) {
				this.response.put("error", "No se encontraron registros");
				return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<List<EntitySecretos>>(this.listaSecretos,HttpStatus.OK);
			
		}
		catch (DataAccessException e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}//end
	
	
	
	// Method Search by ID
	@GetMapping("buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable(name = "id", required = true)Long id){
		
		try {
			this.secreto=this.serviceDaoSecretos.buscarPorId(id);
			if (this.secreto==null) {
				response.put("error", "El secreto no existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<EntitySecretos>(this.secreto,HttpStatus.OK);
		} 
		catch (DataAccessException e) {
			this.response.put("error",  e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	//Method Save 
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar(@Valid @RequestBody EntitySecretos entitySecreto, BindingResult resulValid) {	
		
		
		if (resulValid.hasErrors()) {
			List<String> listErrors=resulValid.getFieldErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			
			this.response.put("error", listErrors);
			return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.BAD_REQUEST);
		}
		
		
		try {
			this.secreto=this.serviceDaoSecretos.guardar(entitySecreto);
			return new ResponseEntity<EntitySecretos>(this.secreto,HttpStatus.CREATED);
			
		} 
		catch (DataAccessException e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}//end
	
	
	
	//Method Update
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editarPorId(@Valid @PathVariable( name = "id", required = true) Long id, @RequestBody EntitySecretos entitySecretos, BindingResult resulValid){
		
		if (resulValid.hasErrors()) {
			List<String> listErrors=resulValid.getFieldErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			
			this.response.put("error", listErrors);
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		try {
			EntitySecretos entitySecretoActualizar=this.serviceDaoSecretos.buscarPorId(id);
			
			
			if (entitySecretoActualizar==null) {
				this.response.put("error", "El secreto no puede ser editado porque no existe");
				return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.NOT_FOUND);
			}
			
			//-- Validar que solamente se pueda editar el secreto si pertenece a la misma fecha
			if (!this.secreto.getfCreacion().equals( LocalDate.now())) {
				System.out.println("Fecha creacion: "+this.secreto.getfCreacion());
				System.out.println("Fecha actual: "+LocalDate.now());
				
				this.response.put("error", "Lo sentimos, este secreto no puede ser eliminado, debido a que pertenece a una fecha diferente a la de hoy");
				return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			 entitySecretoActualizar.setSecreto(entitySecretos.getSecreto());
			 entitySecretoActualizar.setCategoria(entitySecretos.getCategoria());
			 this.secreto=this.serviceDaoSecretos.guardar(entitySecretoActualizar);
			 return new ResponseEntity<EntitySecretos>(this.secreto,HttpStatus.OK);
			
		} 
		catch (DataAccessException e) {
			this.response.put("error",  e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}//end
	
	
	//Method Update whit Admin
	@Secured({"ROLE_ADMIN"})
	@PutMapping("adm/editar/{id}")
	public ResponseEntity<?> editarPorIdWhitAdmin(@Valid @PathVariable( name = "id", required = true) Long id, @RequestBody EntitySecretos entitySecretos, BindingResult resulValid){
		
		if (resulValid.hasErrors() && entitySecretos.getfCreacion()!=null) {
			List<String> listErrors=resulValid.getFieldErrors()
					.stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			
			this.response.put("error", listErrors);
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		try {
			EntitySecretos entitySecretoActualizar=this.serviceDaoSecretos.buscarPorId(id);
			
			
			if (entitySecretoActualizar==null) {
				this.response.put("error", "El secreto no puede ser editado porque no existe");
				return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.NOT_FOUND);
			}
			
			
			
			 entitySecretoActualizar.setSecreto(entitySecretos.getSecreto());
			 entitySecretoActualizar.setCategoria(entitySecretos.getCategoria());
			 if (entitySecretos.getfCreacion()!=null) { //-- Si el Post contiene una fecha la agregamos, si no dejamos la que esta por defecto
				entitySecretoActualizar.setfCreacion(entitySecretos.getfCreacion());
			}
			
			 
			 this.secreto=this.serviceDaoSecretos.guardar(entitySecretoActualizar);
			 return new ResponseEntity<EntitySecretos>(this.secreto,HttpStatus.OK);
			
		} 
		catch (DataAccessException e) {
			this.response.put("error",  e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}//end


	//Method Delate
	@DeleteMapping("eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable(name = "id", required = true) Long id){
		
		
		
		try {
			
			this.secreto=this.serviceDaoSecretos.buscarPorId(id);
			
			if (this.secreto==null) {
				this.response.put("error", "No puedes eliminar este secreto porque no existe");
				return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.NOT_FOUND);	
			}
			
			//-- Validar que solamente se pueda eliminar el secreto si pertenece a la misma fecha
			if (!this.secreto.getfCreacion().equals( LocalDate.now())) {
				System.out.println("Fecha creacion: "+this.secreto.getfCreacion());
				System.out.println("Fecha actual: "+LocalDate.now());
				
				this.response.put("error", "Lo sentimos, este secreto no puede ser eliminado, debido a que pertenece a una fecha diferente a la de hoy");
				return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			this.serviceDaoSecretos.eliminarPorId(id);
			return new ResponseEntity<>(HttpStatus.OK);
			
		} 
		catch (DataAccessException e) {
			this.response.put("error",  e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}//end
	
	
	//Method Delate como admin
		@Secured({"ROLE_ADMIN"})
		@DeleteMapping("adm/eliminar/{id}")
		public ResponseEntity<?> eliminarPorIdWhirAdmin(@PathVariable(name = "id", required = true) Long id){
			
			
			
			try {
				
				this.secreto=this.serviceDaoSecretos.buscarPorId(id);
				
				if (this.secreto==null) {
					this.response.put("error", "No puedes eliminar este secreto porque no existe");
					return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.NOT_FOUND);	
				}
				
				
				this.serviceDaoSecretos.eliminarPorId(id);
				return new ResponseEntity<>(HttpStatus.OK);
				
			} 
			catch (DataAccessException e) {
				this.response.put("error",  e.getMessage());
				return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			catch (Exception e) {
				this.response.put("error", e.getMessage());
				return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}//end
	
}


