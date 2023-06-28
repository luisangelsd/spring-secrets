package com.secrets.dao.controladores;



import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.secrets.dao.modelo.excepciones.RunTimeExceptionNotFound;
import com.secrets.dao.modelo.servicios.IServicesUsuarios;
import com.secrets.dao.oauth2.services.entitys.EntityUsuario;



@CrossOrigin({"*"})
@RestController
@RequestMapping("usuarios/")
public class ControladorUsuarios {
	
	//-- Variables globales
	public Map<String, Object> response=new HashMap<>();
	private EntityUsuario entityUsuario;
	private Random random=new Random();

	
	//-- Inteccion de servicios
	@Qualifier("servicesUsuarios")
	@Autowired
	private IServicesUsuarios serviciosUsuarios;
	
	//--------------------------------------------------------------------------	
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("ver/{username}")
	@ResponseStatus(HttpStatus.OK)
	public EntityUsuario buscarUsuarioByUsername(@PathVariable String username) throws Exception{
		
			//-- Validar que el usuario exista
			this.entityUsuario=this.serviciosUsuarios.buscarUsuarioByUsername(username);
			if (this.entityUsuario==null) {
				throw new RunTimeExceptionNotFound("¡No existe el Usuario!");
			}
					
			//-- Regresar respuesta
			this.entityUsuario.setPassword("");
			return this.entityUsuario;

	}
	
	//--------------------------------------------------------------------------
	
	// No olvides configurar lo siguiente en tu application.properties :)
	//spring.servlet.multipart.max-file-size=10MB
	//spring.servlet.multipart.max-request-size=10MB
	@PostMapping("/imagen-perfil/upload/")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@ResponseStatus(HttpStatus.OK)
	public void editarUrlImagenPerfilUsuario(@RequestParam(name = "archivo") MultipartFile archivo, @RequestParam(name = "username") String username) throws IOException{
		
		this.entityUsuario=null;
	
		//-- Validar cliente
		this.entityUsuario=this.serviciosUsuarios.buscarUsuarioByUsername(username);
		if (this.entityUsuario == null) {
			throw new RunTimeExceptionNotFound("El usuario no Existe");
		}
		
		//-- Creamos la ruta final del archivo requesy
		String nombreArchivo= archivo.getOriginalFilename();
		Path rutaCompletaNueva= Paths.get("simulador-servidor-storage").resolve(nombreArchivo).toAbsolutePath();
			

		//-- Si tiene otra foto establecida por defecto la eliminamos
		if (!this.entityUsuario.getUrlFoto().equalsIgnoreCase("mi-perfil.png")) {
			Path rutaCompletaAnterior=Paths.get("simulador-servidor-storage").resolve(this.entityUsuario.getUrlFoto()).toAbsolutePath(); //-- Amamos rura anterior
			Files.deleteIfExists(rutaCompletaAnterior);																				     //-- Eliminamos si existe
		}
			
				
		//-- Validamos que la imagen no este vacia y guardamos
		if (!archivo.isEmpty()) {
			nombreArchivo=random.nextInt(9000)+"-"+archivo.getOriginalFilename().replace(" ", "-");							//-- Creamos el nuevo nombre del archivo
			rutaCompletaNueva= Paths.get("simulador-servidor-storage").resolve(nombreArchivo).toAbsolutePath(); 			//--  Creamos el nuevo Path completo
			Files.copy(archivo.getInputStream(), rutaCompletaNueva);														//-- Guarda la imagen en la ruta	
			this.serviciosUsuarios.editarUrlImagenPerfilUsuario(username, nombreArchivo); 							//-- Actualizamos registro en la bd 	
		}		

	}
	
	
	//--------------------------------------------------------------------------
	
	//-- Endpoind: Eliminar imagen de perfil
	@DeleteMapping("imagen-perfil/eliminar/{username}")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@ResponseStatus(HttpStatus.OK)
	public void eliminarFotoPerfil(@PathVariable String username) throws IOException{
		
	
			this.entityUsuario=null;
			
			//--- Validar que el usuario exista
			this.entityUsuario=this.serviciosUsuarios.buscarUsuarioByUsername(username);
			if (this.entityUsuario==null) {
				throw new RunTimeExceptionNotFound("Lo sentimos, el usuario no existe");
			} 
			
			
			//--- Eliminar la foto y cambiar la ruta
			if (!this.entityUsuario.getUrlFoto().equalsIgnoreCase("mi-perfil.png")){
				Path ruta= Paths.get("simulador-servidor-storage").resolve(this.entityUsuario.getUrlFoto()).toAbsolutePath();
				Files.deleteIfExists(ruta);
			}
			
	
			//-- Actualizamos registro en la bd
			this.serviciosUsuarios.editarUrlImagenPerfilUsuario(username,"mi-perfil.png");	
			

	}
	
	
	//--------------------------------------------------------------------------
	
	//--Endpoint: Ver imagen
	@GetMapping("imagen-perfil/show/{urlImagen:.+}")
	public ResponseEntity<?> showImagenPerfil(@PathVariable(name = "urlImagen") String urlImagen) throws MalformedURLException {
		
	
			//-- Creamos la ruta completa y configuramos recurso
			Path rutaCompleta= Paths.get("simulador-servidor-storage").resolve(urlImagen).toAbsolutePath(); //-- Creamos la url completa
			Resource recurso=new UrlResource(rutaCompleta.toUri());  										//-- Le asignamos la url del recurso
			
			
			
			//-- Validamos que el recurso exista y sea legible
			if (!recurso.exists() || !recurso.isReadable()) {
				throw new RuntimeException("El recurso no esta diponible: No existe o no es legible");
			}
			
			
			//-- Configuramos las cabeceras para forzar la descarga
			HttpHeaders cabeceras= new HttpHeaders();
			cabeceras.add(HttpHeaders.CONTENT_DISPOSITION, "attachment-, filename=\""+recurso.getFilename()+"\"");
			
	
			return new ResponseEntity<Resource>(recurso,cabeceras, HttpStatus.OK);
	
	}
	

}
