package com.secrets.dao;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class MicroservicioDaoSecretsApplication implements CommandLineRunner {




	public static void main(String[] args) {		
		SpringApplication.run(MicroservicioDaoSecretsApplication.class, args);
	}


	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Override //-- Imprimir en pantalla contraseñas encriptadad = 123
	public void run(String... args) throws Exception {
		String password="123";
		for(int i=0; i<4;i++) {
			String passEncry= passwordEncoder.encode(password);
			System.out.println(passEncry);
		}
	}

	
	/* 
	  =====1) Agrega las siguientes dependencias:
	 
		 
		<!-- https://mvnrepository.com/artifact/org.springframework.security.oauth/spring-security-oauth2 -->
			<dependency>
			    <groupId>org.springframework.security.oauth</groupId>
			    <artifactId>spring-security-oauth2</artifactId>
			    <version>2.3.8.RELEASE</version>
			</dependency>
			<!-- https://mvnrepository.com/artifact/org.springframework.security/spring-security-jwt -->
			<dependency>
			    <groupId>org.springframework.security</groupId>
			    <artifactId>spring-security-jwt</artifactId>
			    <version>1.1.1.RELEASE</version>
			</dependency>
			<!-- https://mvnrepository.com/artifact/org.glassfish.jaxb/jaxb-runtime -->
			<dependency>
	 		   <groupId>org.glassfish.jaxb</groupId>
	 		   <artifactId>jaxb-runtime</artifactId>
			</dependency>

		=====2) Cambia la version de SpringBoot <version>2.5.3</version>
		
		===== 3) Configura tus Entitys 
		
		===== 4) Configura tu servicio usuario "Servicios Usuarios"  implementando UserDetailsService, aquí se realiza la consulta que valida si el usuario existe o no
		
		===== 5) Configurar la classe: SpringSecurityConfig
		
		
		===== 6) Configurar la classe: AuthorizationServerConfig
		
		
		Nota: Para el punto 8 solo puedes eleguir una configuración
		===== 7) Configura la clase "ResourceServerConfig": Aquí defines que rutas seran publicas como los metodos permitidos GETL, POST, ETC. 
		===== 7) Agregar seguridad a nuestras rutas con HttpSecurity Aquí defines que rutas seran publicas como los metodos permitidos GETL, POST, ETC.
				
				7.1) En la clase de "Spring Security Config" añadimos: @EnableGlobalMethodSecurity(securedEnabled=true)
				7.2) Configuramos nuestros endpointds con la siguiente configuración, variandola dependiendo el caso
					@Secured({"ROLE_USER","ROLE_ADMIN"})
					@Secured({"ROLE_USER"})
					@Secured({"ROLE_ADMIN"})
		*/
	
	
	
	/* ==== Para probar necesitamos un par de registros, como user y admin - Recuerda que las constraseñas son encriptadas ====
	  
		INSERT INTO USUARIOS (username, password, enabled, descripcion_perfil) VALUES('admin1','123',1,'Sin descripcion');
        INSERT INTO USUARIOS (username, password, enabled, descripcion_perfil) VALUES('user1','123',1,'Sin descripcion');
        INSERT INTO ROLES (nombre) VALUES('ROLE_ADMIN');
		INSERT INTO ROLES (nombre) VALUES('ROLE_USER');
        INSERT INTO USUARIOS_ROLES (usuario_id, rol_id) VALUES(1,1);
       INSERT INTO USUARIOS_ROLES (usuario_id, rol_id) VALUES(2,2);
	 
	 
Todas estas contraseñas son: 123	 
$2a$10$GCf2rbx7AQvyQ/SsCvPtBOMYPYTRRw/P7uIGhwwcurQU8Ecar1HMq 
$2a$10$TJAbrbbrAX5FSdluDIRBUunomT5q/EkA632n6EVpBspmhH.b2f.n.
$2a$10$8HIah2lUmOFQ/zBnUv1zTeJKxGYSPc1ogil1JHXdSLF/9tqS28alC
$2a$10$MAD6VBLGOV9cVXvZqIsk7.V2SbDNPbjYxhf40rfxt1qpDRbOm1qP2


	 * */
	
	
}
