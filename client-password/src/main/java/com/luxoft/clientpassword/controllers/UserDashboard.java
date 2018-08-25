package com.luxoft.clientpassword.controllers;

import com.luxoft.clientpassword.security.ClientUserDetails;
import com.luxoft.clientpassword.user.ClientUser;
import com.luxoft.clientpassword.user.Entry;
import com.luxoft.clientpassword.user.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class UserDashboard {

	@Autowired
	private OAuth2RestTemplate restTemplate;

	@Autowired
	private AccessTokenRequest accessTokenRequest;

	@GetMapping("/")
	public String home() { return "index"; }

	@GetMapping("/callback")
	public ModelAndView callback() {
		return new ModelAndView("forward:/dashboard");
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboard() {
		ClientUserDetails userDetails = (ClientUserDetails) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
		ClientUser clientUser = userDetails.getClientUser();
		clientUser.setEntries(Arrays.asList(
				new Entry("entry 1"),
				new Entry("entry 2")));

		ModelAndView mv = new ModelAndView("dashboard");
		mv.addObject("user", clientUser);

		tryToGetUserProfile(mv);

		return mv;

	}

	private void tryToGetUserProfile(ModelAndView mv) {
		accessTokenRequest.add("username", "user1");
		accessTokenRequest.add("password", "123");

		String endpoint = "http://localhost:8081/api/profile";
		try {
			UserProfile userProfile =
					restTemplate.getForObject(endpoint, UserProfile.class);
			mv.addObject("profile", userProfile);

		} catch (HttpClientErrorException e) {
			throw new RuntimeException("it was not possible to retrieve user profile");
		}
	}
}
