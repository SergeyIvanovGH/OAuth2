package com.luxoft.refreshtoken.controllers;

import com.luxoft.refreshtoken.dto.AccessToken;
import com.luxoft.refreshtoken.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/oauth/accesstoken/new")
public class OAuthController {

	private RefreshTokenService tokenService;

	@RequestMapping(path = "/get")
	public AccessToken getNewAccessToken(@RequestParam(name = "refresh_token", required = true) String refreshToken) {
		return tokenService.getNewAccessToken(refreshToken);
	}

	@Autowired
	public void setTokenService(RefreshTokenService tokenService) {
		this.tokenService = tokenService;
	}
}
