package com.secrets.dao.controladores;



import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.secrets.dao.modelo.entitys.EntityUsuario;
import com.secrets.dao.modelo.servicios.IServicesUsuarios;

@RestController
@RequestMapping("adm/")
public class ControladorUsuarios {
	
	//-- Variables globales
	public Map<String, Object> response=new HashMap<>();
	private EntityUsuario entityUsuario;
	private Random random=new Random();
	
	//-- Inteccion de servicios
	@Qualifier("servicesUsuarios")
	@Autowired
	private IServicesUsuarios serviciosUsuarios;
	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("ver/usuario/{username}")
	public ResponseEntity<?> verUsuario(@PathVariable(name = "username")String username){
		
		
		try {
			
			//-- Validar que el usuario exista
			this.entityUsuario=this.serviciosUsuarios.buscarUserPorUsername(username);
			if (this.entityUsuario==null) {
				this.response.put("error", "El usuario no existe");
				return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			//-- Regresar respuesta
			this.entityUsuario.setPassword("");
			return new ResponseEntity<EntityUsuario>(this.entityUsuario, HttpStatus.OK);

		} catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	//Endpoint: Upload Imagen
	// No olvides configurar lo siguiente en tu application.properties :)
	//spring.servlet.multipart.max-file-size=10MB
	//spring.servlet.multipart.max-request-size=10MB
	@PostMapping("/imagen-perfil/upload/")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> editarImagen(@RequestParam(name = "archivo") MultipartFile archivo, @RequestParam(name = "username") String username){
		

		try {
			
			String nombreArchivo=archivo.getOriginalFilename();												 //-- Obtenemos nombre archivo y lo modificamos
			Path ruta= Paths.get("simulador-servidor-storage").resolve(nombreArchivo).toAbsolutePath();		 //-- Armamos la ruta de la imagen subida para despues validar si existe y eliminarla
			
			
			//-- Validar cliente
			this.entityUsuario=this.serviciosUsuarios.buscarUserPorUsername(username);
			if (this.entityUsuario==null) {
				this.response.put("error", "El usuario no Existe");
				return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			//-- Si tiene otra foto establecida por defecto la eliminamos
			if (!this.entityUsuario.getUrlFoto().equalsIgnoreCase("mi-perfil.png")) {
				Path rutaAnterior=Paths.get("simulador-servidor-storage").resolve(this.entityUsuario.getUrlFoto()).toAbsolutePath(); //-- Amamos rura anterior
				Files.deleteIfExists(rutaAnterior);																				     //-- Eliminamos si existe
			}
			
			
			
			
			//-- Validamos que la imagen no este vacia y guardamos
			if (!archivo.isEmpty()) {
				nombreArchivo=random.nextInt(9000)+"-"+archivo.getOriginalFilename().replace(" ", "-");							//-- Creamos el nuevo nombre del archivo
				ruta= Paths.get("simulador-servidor-storage").resolve(nombreArchivo).toAbsolutePath(); 			//--  Creamos el nuevo Path completo
				Files.copy(archivo.getInputStream(), ruta);														//-- Guarda la imagen en la ruta	
				this.serviciosUsuarios.editarFotoPorUsername(username, nombreArchivo); 							//-- Actualizamos registro en la bd 	
			}	
			
			
			return new ResponseEntity<>(HttpStatus.OK);
			
			
		} 		
		catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	
	@DeleteMapping("imagen-perfil/eliminar/")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> eliminarFotoPerfil(@RequestParam(name = "username", required = true)String username){
		
		try {
			
			//--- Validar que el usuario exista
			this.entityUsuario=this.serviciosUsuarios.buscarUserPorUsername(username);
			if (this.entityUsuario==null) {
				this.response.put("error", "Lo sentimos, el usuario no existe");
				return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.INTERNAL_SERVER_ERROR);
			} 
			
			
			//--- Si la imagen es la que tiene por defecto no se realiza ningun cambio
			if (this.entityUsuario.getUrlFoto().equalsIgnoreCase("mi-perfil.png")){
				return new ResponseEntity<>(HttpStatus.OK);
			}
			
			
			//--- Eliminar la foto y cambiar la ruta
			Path ruta= Paths.get("simulador-servidor-storage").resolve(this.entityUsuario.getUrlFoto()).toAbsolutePath();
			Files.deleteIfExists(ruta);
			
			
			//-- Actualizamos registro en la bd
			this.serviciosUsuarios.editarFotoPorUsername(username,"mi-perfil.png");	
			
			
			//-- Regresamos respuesta
			return new ResponseEntity<>(HttpStatus.OK);
			
			
			
		} catch (Exception e) {
			this.response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(this.response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	

	

}
