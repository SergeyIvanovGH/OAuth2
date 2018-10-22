package com.luxoft.refreshtoken.configuration;

import com.luxoft.refreshtoken.filters.OAuthTokenPreZuulFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FiltersConfiguration {

	@Bean
	public OAuthTokenPreZuulFilter oAuthPreZuulFilter() {
		return new OAuthTokenPreZuulFilter();
	}
}
