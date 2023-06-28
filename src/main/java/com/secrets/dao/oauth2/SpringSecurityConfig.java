package com.secrets.dao.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.secrets.dao.oauth2.services.ServiceOauth2;


@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	//-- Inyectamos nuestro servicios
	@Autowired
	private ServiceOauth2 servicioOauthUsuario;
	
	
	
	//-- Creamos un metodo para encriptar nuestras contraseñas
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	//-- Creamos un metodo para encriptar nuestras contraseñas
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.servicioOauthUsuario)
		.passwordEncoder(passwordEncoder());
	}


	//-- Sobre escribimos el metodo y lo dejamos como esta
	@Bean(name = "authenticationManager")
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	
	//-- Crear metodo: Esta configuración le dice a Spring que vamos a trabajr con tokens y no con sessiones
	// Cuando tenemos el back y el front en Spring no es necesario, pero  trabajaremos con un front externo
	// Tambine ayuda a proteger nuestro back contra ataques
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//-- Indica que cualquier otra pagina no señalada arriba es privada
	}
	
	
	
}
