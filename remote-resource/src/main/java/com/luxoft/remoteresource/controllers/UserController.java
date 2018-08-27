package com.luxoft.remoteresource.controllers;

import com.luxoft.remoteresource.api.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class UserController {

	@CrossOrigin
	@RequestMapping("/profile")
	public ResponseEntity<UserProfile> profile() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String email = user.getUsername() + "@gmail.com";

		UserProfile userProfile = new UserProfile();
		userProfile.setName(user.getUsername());
		userProfile.setEmail(email);

		return ResponseEntity.ok(userProfile);
	}
}
