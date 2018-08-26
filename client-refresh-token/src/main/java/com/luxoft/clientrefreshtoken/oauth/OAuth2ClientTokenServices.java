package com.luxoft.clientrefreshtoken.oauth;

import com.luxoft.clientrefreshtoken.security.ClientUserDetails;
import com.luxoft.clientrefreshtoken.user.ClientUser;
import com.luxoft.clientrefreshtoken.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class OAuth2ClientTokenServices implements ClientTokenServices {
	@Autowired
	private UserRepository userRepository;

	private ClientUser getClientUser(Authentication authentication) {
		ClientUserDetails loggedUser = (ClientUserDetails) authentication.getPrincipal();
		Long userId = loggedUser.getClientUser().getId();

		return userRepository.findOne(userId);
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails,
	                                        Authentication authentication) {
		ClientUser clientUser = getClientUser(authentication);

		String accessToken = clientUser.getAccessToken();

		Calendar expirationDate = clientUser.getAccessTokenValidity();

		if (accessToken == null) {
			return null;
		}

		DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
		oAuth2AccessToken.setExpiration(expirationDate.getTime());
		oAuth2AccessToken.setRefreshToken(new DefaultOAuth2RefreshToken(clientUser.getRefreshToken()));

		return oAuth2AccessToken;
	}

	@Override
	public void saveAccessToken(OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails,
	                            Authentication authentication,
	                            OAuth2AccessToken oAuth2AccessToken) {

		Calendar expirationDate = Calendar.getInstance();
		expirationDate.setTime(oAuth2AccessToken.getExpiration());

		ClientUser clientUser = getClientUser(authentication);
		clientUser.setAccessToken(oAuth2AccessToken.getValue());
		clientUser.setAccessTokenValidity(expirationDate);
		clientUser.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());

		userRepository.save(clientUser);
	}

	@Override
	public void removeAccessToken(OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails,
	                              Authentication authentication) {

		ClientUser clientUser = getClientUser(authentication);
		clientUser.setAccessToken(null);
		clientUser.setRefreshToken(null);
		clientUser.setAccessTokenValidity(null);

		userRepository.save(clientUser);
	}
}
