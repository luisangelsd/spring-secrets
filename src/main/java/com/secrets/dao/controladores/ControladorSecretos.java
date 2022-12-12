package com.secrets.dao.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.secrets.dao.modelo.servicios.ServicioDaoSecretos;

@CrossOrigin({"*"})
@RestController
@RequestMapping("")
public class ControladorSecretos {
	
	
	@Autowired
	public ServicioDaoSecretos servicioDaoSecretos;
	
	Map<String, Object> response=new HashMap<>();
	List<EntitySecretos> listaSecretos=null;
	EntitySecretos secreto=null;
	
	
	// Method List
	@GetMapping("/listar")
	public ResponseEntity<?> listar(){
		
		
		try {
			
			this.listaSecretos=servicioDaoSecretos.listarTodos();
			
			if (this.listaSecretos.isEmpty()) {
				this.response.put("response", "No se han encontrado registros");
				return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<List<EntitySecretos>>(this.listaSecretos,HttpStatus.OK);			
		} catch (DataAccessException e) {
			this.response.put("response", "e");
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}//end
	
	
	
	
	// Method List by Category
	@GetMapping("/listar/{categoria}")
	public ResponseEntity<?> listarPorCategoria(@PathVariable(name = "categoria") String categoria){
		
		try {
			this.listaSecretos=servicioDaoSecretos.listarPorCategoria(categoria);
			
			if (this.listaSecretos.isEmpty()) {
				this.response.put("response", "No se encontraron registros");
				return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<List<EntitySecretos>>(this.listaSecretos,HttpStatus.OK);
			
		} catch (DataAccessException e) {
			
			this.response.put("response", e);
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}//end
	
	
	
	// Method Search by ID
	@GetMapping("buscar/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable(name = "id")Long id){
		
		try {
			this.secreto=servicioDaoSecretos.buscarPorId(id);
			if (this.secreto==null) {
				response.put("response", "El secreto no existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<EntitySecretos>(this.secreto,HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("response", e);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	
	//Method Save 
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar(@Valid @RequestBody EntitySecretos entitySecreto, BindingResult resulValid) {	
		
		
		if (resulValid.hasErrors()) {
			this.response.put("response", "Datos invalidos y/o nulos");
			return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			this.secreto=servicioDaoSecretos.guardar(entitySecreto);
			return new ResponseEntity<EntitySecretos>(this.secreto,HttpStatus.CREATED);
			
		} catch (DataAccessException e) {
			this.response.put("response", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}//end
	
	
	
	//Method Update
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> editarPorId(@Valid @PathVariable( name = "id") Long id, @RequestBody EntitySecretos entitySecretos, BindingResult resulValid){
		

		
		if (resulValid.hasErrors()) {
			this.response.put("response", "Datos invalidos y/o nulos");
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		try {
			EntitySecretos entitySecretoActualizar=servicioDaoSecretos.buscarPorId(id);
			
			
			if (entitySecretoActualizar==null) {
				this.response.put("response", "El secreto no puede ser editado porque no existe");
				return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.NOT_FOUND);
			}
			
			 entitySecretoActualizar.setSecreto(entitySecretos.getSecreto());
			 entitySecretoActualizar.setCategoria(entitySecretos.getCategoria());
			 this.secreto=servicioDaoSecretos.guardar(entitySecretoActualizar);
			 return new ResponseEntity<EntitySecretos>(this.secreto,HttpStatus.OK);
			
		} catch (DataAccessException e) {
			this.response.put("response", e);
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}//end


	//Method Delate
	@DeleteMapping("eliminar/{id}")
	public ResponseEntity<?> eliminarPorId(@PathVariable(name = "id") Long id){
		
		
		
		try {
			
			boolean validarExiste=servicioDaoSecretos.existePorId(id);
			
			if (!validarExiste) {
				this.response.put("response", "No puedes eliminar este secreto porque no existe");
				return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.NOT_FOUND);	
			}
			
			servicioDaoSecretos.eliminarPorId(id);
			return new ResponseEntity<>(HttpStatus.OK);
			
		} catch (DataAccessException e) {
			this.response.put("response", e);
			return new ResponseEntity<Map<String, Object>>(this.response,HttpStatus.INTERNAL_SERVER_ERROR);
		}

		
	}//end
	
}


