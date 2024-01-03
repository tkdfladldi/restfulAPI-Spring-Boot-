package com.example.park.myrestfulservice.config.securityConfig;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
	
	private String phoneNo = null;
	
	public CustomAuthenticationToken(Object principal, Object credentials, Collection<GrantedAuthority> authorities) {
		super (principal, credentials, authorities);
	}
	public CustomAuthenticationToken (String username, String password, String phoneNo) {
		super (username, password);
		this.phoneNo = phoneNo;
	}
	
	
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo (String phoneNo) {
		this.phoneNo = phoneNo;
	}
}





