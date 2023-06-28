package com.secrets.dao.oauth2;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//-- Aquí cinfiguraremos las rutas y los permisos de nuestros endpoinds
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter  {
	
	
	
		//-- Sobrescribimos el metodo "configure(HttpSecurity http)" y lo configuramos
		//-- Comente el codigo ya que voy a probar otra alternativa con anotaciónes, pero aún así lo demas es necesario
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			.antMatchers(HttpMethod.GET,"/listar/**").permitAll() //-- Si no se marca el metodo se le asignan todos
			.antMatchers(HttpMethod.GET,"/buscar/**").permitAll()
			.antMatchers(HttpMethod.POST,"/guardar/**").permitAll()
			.antMatchers(HttpMethod.PUT,"/editar/**").permitAll()
			.antMatchers(HttpMethod.GET,"/adm/imagen-perfil/show/**").permitAll()
			.antMatchers("/v3/api-docs/**").permitAll()
			.antMatchers("/swagger-ui/**").permitAll()
			.antMatchers("/h2-console/**").permitAll()
			/*.antMatchers(HttpMethod.GET,"/private/**").hasAnyRole("USER", "ADMIN")
			.antMatchers(HttpMethod.POST,"/private/**").hasRole("ADMIN")*/					
			.anyRequest().authenticated()									//-- Indica que cualquier otra pagina no señalada arriba es privada
			.and().cors().configurationSource(corsConfigurationSource());	//-- Enviamos la configuración de corts
		}

		
		@Bean //-- Esta configuación es para los corts
		public CorsConfigurationSource corsConfigurationSource() {
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); //-- Utiliza * para todos
			config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE", "OPTIONS"));   //-- Utiliza * para todos
			config.setAllowCredentials(true);
			config.setAllowedHeaders(Arrays.asList("Content-Type","Authorization"));
			
			UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();  //-- Configuramos y refresamos respuesta
			source.registerCorsConfiguration("/**", config);
			return source;
		}
		
		@Bean// Se aplica cuando accedemos a los endponds, generar nuestro token o validarlo
		public FilterRegistrationBean<CorsFilter> corsFilter(){
			FilterRegistrationBean<CorsFilter> bean=new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
			bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
			return bean;
		}
		
}
