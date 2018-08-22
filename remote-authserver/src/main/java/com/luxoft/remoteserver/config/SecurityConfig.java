package com.luxoft.remoteserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

/*
	@Autowired
	private ResourseOwnerDetailsService detailsService;
*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.requestMatchers()
				.antMatchers("/login", "/oauth/authorize")
				.and()
				.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.formLogin()
//					.permitAll()
		;

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
//				.userDetailsService(detailsService);
	}
}
