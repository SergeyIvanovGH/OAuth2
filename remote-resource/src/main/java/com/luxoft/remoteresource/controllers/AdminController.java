package com.luxoft.remoteresource.controllers;

import com.luxoft.remoteresource.api.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class AdminController {

	@RequestMapping("/users")
	public ResponseEntity<List<UserProfile>> getAllUsers() {
		return ResponseEntity.ok(getUsers());
	}

	private List<UserProfile> getUsers() {
		List<UserProfile> users = new ArrayList<>();

		users.add(new UserProfile("user number One", "user1@gmail.com"));
		users.add(new UserProfile("user number Two","user2@gmail.com"));
		users.add(new UserProfile("user number Three","user3@gmail.com"));

		return users;
	}
}
