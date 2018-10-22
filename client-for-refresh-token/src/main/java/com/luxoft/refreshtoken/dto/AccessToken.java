package com.luxoft.refreshtoken.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class AccessToken {

	public static String BEARER_TYPE = "Bearer";

	@JsonProperty(value = "access_token")
	private String value;

	@JsonProperty(value = "expires_in")
	private Long expiration;

	@JsonProperty(value = "token_type")
	private String tokenType = BEARER_TYPE.toLowerCase();

	@JsonProperty(value = "refresh_token")
	private String refreshToken;

	private Set<String> scope;



	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getExpiration() {
		return expiration;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScope() {
		StringBuffer scopes = new StringBuffer();
		for (String s : scope) {
			scopes.append(s);
			scopes.append(" ");
		}
		return scopes.substring(0, scopes.length() - 1);
	}

	public void setScope(String scopes) {
		Set<String> scope = new TreeSet<String>();
		for (StringTokenizer tokenizer = new StringTokenizer(scopes, " ,"); tokenizer
				.hasMoreTokens();) {
			scope.add(tokenizer.nextToken());
		}

		this.scope = scope;
	}

	@Override
	public String toString() {
		return "AccessToken{" +
				"value='" + value + '\'' +
				", expiration=" + expiration +
				", tokenType='" + tokenType + '\'' +
				", refreshToken='" + refreshToken + '\'' +
				", scope=" + scope +
				'}';
	}
}
