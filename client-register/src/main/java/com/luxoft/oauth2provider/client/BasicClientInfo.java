package com.luxoft.oauth2provider.client;

import java.util.Arrays;

public class BasicClientInfo {

	private String name;
	private String redirectUri;
	private Scope[] scopes;

	public Scope[] getScope() {
		return scopes;
	}

	public void setScope(Scope[] scopes) {
		this.scopes = scopes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Scope[] getScopes() {
		return scopes;
	}

	public void setScopes(Scope[] scopes) {
		this.scopes = scopes;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}


	@Override
	public String toString() {
		return "BasicClientInfo{" +
				"name='" + name + '\'' +
				", redirectUri='" + redirectUri + '\'' +
				", scopes=" + Arrays.toString(scopes) +
				'}';
	}

}
