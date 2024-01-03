package com.example.park.myrestfulservice.config.securityConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private static final long serialVersionUID = -1L;
	private static final String PHONE_NO = "phoneNo";
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException { 
		CustomAuthenticationToken authRequest = getAuth(request); 
		setDetails(request, authRequest);
		return getAuthenticationManager().authenticate (authRequest);
	}
	
	private CustomAuthenticationToken getAuth(final HttpServletRequest request) {
		String username = obtainUsername(request);
		String password = obtainPassword (request);
		String phonno = obtainPhoneNo (request);
		
		return new CustomAuthenticationToken(username, password, phonno);
	}
	@Nullable
	protected String obtainPhoneNo (final HttpServletRequest request) { 
		return request.getParameter(PHONE_NO);
	}
	
	@Autowired
	@Override
	public void setAuthenticationManager (AuthenticationManager authenticationManager) { 
		super.setAuthenticationManager (authenticationManager);
	}
}




