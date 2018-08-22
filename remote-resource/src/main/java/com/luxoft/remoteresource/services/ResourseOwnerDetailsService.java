package com.luxoft.remoteresource.services;

import com.luxoft.remoteresource.entity.ResourceOwner;
import com.luxoft.remoteresource.repository.ResourceOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ResourseOwnerDetailsService implements UserDetailsService {

	@Autowired
	private ResourceOwnerRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<ResourceOwner> resourceOwner = repository.findByUsername(username);

		resourceOwner
				.orElseThrow(() -> new UsernameNotFoundException("Username not found!"));

		return new User(resourceOwner.get().getUsername(),
				resourceOwner.get().getPassword(), new ArrayList<>());
	}
}
