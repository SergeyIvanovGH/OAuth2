package com.luxoft.clientrefreshtoken.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class ClientConfiguration {


	@Autowired
	private ClientTokenServices clientTokenServices;

	@Autowired
	private OAuth2ClientContext oauth2ClientContext;


	@Bean
	public OAuth2ProtectedResourceDetails authorizationCode() {
		AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();

		resourceDetails.setId("oauth2server");
		resourceDetails.setTokenName("oauth_token");
		resourceDetails.setClientId("client_ref");
		resourceDetails.setClientSecret("ref123");
		resourceDetails.setAccessTokenUri("http://localhost:8080/oauth/token");
		resourceDetails.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");
		resourceDetails.setScope(Arrays.asList("read_profile"));
		resourceDetails.setPreEstablishedRedirectUri("http://localhost:9000/callback");
		resourceDetails.setUseCurrentUri(false);
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);

		return resourceDetails;
	}

	@Bean
	public OAuth2RestTemplate oauth2RestTemplate() {
		OAuth2ProtectedResourceDetails resourceDetails = authorizationCode();

		OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails, oauth2ClientContext);
		AccessTokenProviderChain provider = new
				AccessTokenProviderChain(Arrays.asList(new AuthorizationCodeAccessTokenProvider()));
		provider.setClientTokenServices(clientTokenServices);

		template.setAccessTokenProvider(provider);

		return template;
	}
}
