package com.secrets.dao.oauth2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired //-- Inyectamos nuestros metodo de la clase "SpringSecurityConfig"
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired //-- Inyectamos nuestros metodo de la clase "SpringSecurityConfig"
	@Qualifier(value = "authenticationManager")
	private AuthenticationManager authenticationManager;

	
	//======== Implementamos los siguientes metodos de la clase "AuthorizationServerConfigurerAdapter"

	
	//-- Primer metodo: Aquí configuramos quien puede solicitar el acceso para generar un token
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()")//-- end pont para generar el token (oauth/token)
		.checkTokenAccess("isAuthenticated"); //-- Valida el token
	}

	//-- Segundo metodo: Configura el acceso a los clientes que tendras acceso (frintend), usuario y contraseña
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("angularapp").secret(passwordEncoder.encode("87654321"))
		.scopes("read", "write")
		.authorizedGrantTypes("password","refresh_token") //-- Aun. por password y refrescaremos el token
		.accessTokenValiditySeconds(3600) //-- El tocken es valido por 1 hora
		.refreshTokenValiditySeconds(3600) //-- Refresca el toquen cada 1 hora
		.and()
		.withClient("postmanapp").secret(passwordEncoder.encode("123"))
		.scopes("read", "write")
		.authorizedGrantTypes("password","refresh_token")
		.accessTokenValiditySeconds(3600)
		.refreshTokenValiditySeconds(3600);
	}

	//-- Esta configuración es para preprar la información a añadir al token, importante si no te marca el error "unsupported_grant_type"
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints.authenticationManager(this.authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter()); //-- Añadimos la configuración para añadir mas info al token
	}

	
	//======== Implementamos los siguientes metodos ========================="
	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore( accessTokenConverter());
	}
	
	@Bean //-- Este te permite agregar una firma adicion - Esta es obcional pero brinda mas información
	public JwtAccessTokenConverter accessTokenConverter(){
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey("alguna-firma-retoma-las-clases-pendientes-para-configrarlo");					//-- Añadimos la firma de nuestro token
		return tokenConverter;
	}
}
