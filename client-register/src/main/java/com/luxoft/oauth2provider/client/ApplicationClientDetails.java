package com.luxoft.oauth2provider.client;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class ApplicationClientDetails implements ClientDetails {

	private String clientId;
	private String clientSecret;
	private Set<String> resourceIds = Collections.emptySet();
	private Set<String> scope = Collections.emptySet();
	private Set<String> webServerRedirectUri = Collections.emptySet();
	private int accessTokenValidity;
	private Map<String, Object> additionalInformation = new HashMap<>();
	private Set<String> grantTypes = Collections.emptySet();

	public void setName(String name) {
		additionalInformation.put("name", name);
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setAccessTokenValidity(int accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public void addRedirectUri(String redirectUri) {
		this.webServerRedirectUri.add(redirectUri);
	}

	public void addScope(String scope) {
		this.scope.add(scope);
	}

	public void addResourceId(String resourceId) {
		this.resourceIds.add(resourceId);
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return resourceIds;
	}

	@Override
	public boolean isSecretRequired() {
		return true;
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}

	@Override
	public boolean isScoped() {
		return scope.size()>0;
	}

	@Override
	public Set<String> getScope() {
		return scope;
	}

	public void setGrantTypes(Set<String> grantTypes) {
		this.grantTypes = grantTypes;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return grantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return webServerRedirectUri;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return new HashSet<>();
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return accessTokenValidity;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return null;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		return additionalInformation;
	}
}
