package com.luxoft.refreshtoken.services;

import com.luxoft.refreshtoken.dto.AccessToken;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Arrays;

public class RefreshTokenServiceTest {

	private String uri = "http://ddigitapu02.alfa.bank.int:8091/oauth/token";

	@Test
	public void getAccessToenByRefreshToken() throws UnsupportedEncodingException {

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>(1);
		body.add("grant_type", "refresh_token");
		body.add("refresh_token", "8f1a7b90-65d8-4eb3-9368-32de73ce22a5");
		body.add("scope", "client");


		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


		RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		RestTemplate template = restTemplateBuilder
				.basicAuthorization("alfa-mobile", "N62sqESbcw93rTcbjQXuwAnierurv23wR")
				.build();

		template.setMessageConverters(Arrays.asList(new FormHttpMessageConverter(), new MappingJackson2HttpMessageConverter()));

		RequestEntity<MultiValueMap<String, String>> bodyRequestEntity = new RequestEntity<>(body, headers, HttpMethod.POST, URI.create(uri));

		ResponseEntity<AccessToken> response = template.exchange(bodyRequestEntity, AccessToken.class);

		System.out.println(response);
	}
}