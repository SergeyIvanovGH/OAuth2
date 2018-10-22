package com.luxoft.refreshtoken.services;

import com.luxoft.refreshtoken.dto.AccessToken;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Arrays;

@Service
public class RefreshTokenService {

	public AccessToken getNewAccessToken(@NotNull String token) {

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>(1);
		body.add("grant_type", "refresh_token");
		body.add("refresh_token", token);
		body.add("scope", "client");

		String uri = "http://ddigitapu02.alfa.bank.int:8091/oauth/token";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate template = restTemplateBuilder
				.basicAuthorization("alfa-mobile", "N62sqESbcw93rTcbjQXuwAnierurv23wR")
				.build();

		template.setMessageConverters(Arrays.asList(new FormHttpMessageConverter(), new MappingJackson2HttpMessageConverter()));

		RequestEntity<MultiValueMap<String, String>> bodyRequestEntity = new RequestEntity<>(body, headers, HttpMethod.POST, URI.create(    uri));

		ResponseEntity<AccessToken> response = template.exchange(bodyRequestEntity, AccessToken.class);

		return response.getBody();
	}
}
