package com.luxoft.refreshtoken.filters;

import com.luxoft.refreshtoken.configuration.OAuth2ClientProperty;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;

import java.io.UnsupportedEncodingException;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


public class OAuthTokenPreZuulFilter extends ZuulFilter {

	private OAuth2ClientProperty properties;

	private static final Logger LOGGER = LogManager.getLogger(OAuthTokenPreZuulFilter.class);

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return PRE_DECORATION_FILTER_ORDER - 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext ctx = RequestContext.getCurrentContext();
		if (ctx.getRequest().getRequestURI().contains("oauth/token")) {
			byte[] encoded;
			try {
				String clientIdSecret = new String(properties.getClientId() + ":" + properties.getClientSecret());

				encoded = Base64.encode(clientIdSecret.getBytes("UTF-8"));

				ctx.addZuulRequestHeader("Authorization", "Basic " + new String(encoded));

			} catch (UnsupportedEncodingException e) {
				LOGGER.error("Error occured in pre filter", e);
			}
		}
		return null;
	}

	@Autowired
	public void setProperties(OAuth2ClientProperty properties) {
		this.properties = properties;
	}
}
