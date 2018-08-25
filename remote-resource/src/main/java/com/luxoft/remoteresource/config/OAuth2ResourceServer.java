package com.luxoft.remoteresource.config;

import com.luxoft.remoteresource.services.ResourseOwnerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.*;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

	@Autowired
	private ResourseOwnerDetailsService userDetailsService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.anyRequest().authenticated().and()
				.requestMatchers().antMatchers("/api/**")
//				.and().cors()
		;
	}

	@Bean
	public RemoteTokenServices remoteTokenServices() {
		RemoteTokenServices tokenServices = new RemoteTokenServices();
		tokenServices.setClientId("resource_server");
		tokenServices.setClientSecret("abc123");
		tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
		tokenServices.setAccessTokenConverter(accessTokenConverter());
		return tokenServices;
	}

	@Bean
	public AccessTokenConverter accessTokenConverter() {
		DefaultAccessTokenConverter converter = new DefaultAccessTokenConverter();
		converter.setUserTokenConverter(userTokenConverter());
		return converter;
	}

	@Bean
	public UserAuthenticationConverter userTokenConverter() {
		DefaultUserAuthenticationConverter converter = new DefaultUserAuthenticationConverter();
		converter.setUserDetailsService(userDetailsService);
		return converter;
	}
}
