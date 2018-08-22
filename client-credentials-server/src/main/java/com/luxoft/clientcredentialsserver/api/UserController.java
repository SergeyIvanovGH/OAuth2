package com.luxoft.clientcredentialsserver.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

	@RequestMapping("/user")
	public ResponseEntity<UserProfile> user() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = user.getUsername() + "@mailinter.com";

		UserProfile userProfile = new UserProfile(user.getUsername(), email);

		return ResponseEntity.ok(userProfile);
	}
}
