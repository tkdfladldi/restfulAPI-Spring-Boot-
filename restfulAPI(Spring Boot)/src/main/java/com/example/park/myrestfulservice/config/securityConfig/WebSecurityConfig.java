package com.example.park.myrestfulservice.config.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled= true) 
public class WebSecurityConfig {
	@Autowired
	private CustomAuthenticationProvider authProvider;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() { 
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authManagerBuilder.authenticationProvider (authProvider);
		return authManagerBuilder.build();
	}
	
	@Bean
	public CustomAuthenticationFilter authenticationFilter() { 
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(successHaandler());
		filter.setAuthenticationFailureHandler(failureHandler());
		return filter;
	}
	
	@Bean
	public CustomAuthenticationSuccessHandler successHaandler() { 
		return new CustomAuthenticationSuccessHandler("/home");
	}
	
	@Bean
	public CustomAuthenticationFailureHandler failureHandler() { 
		return new CustomAuthenticationFailureHandler();
	}
	
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws Exception { 
		//http.headers (headers-> headers.xssProtection());
		
		http.csrf().disable();
		
		http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests (authorize -> authorize.antMatchers("/login").permitAll().anyRequest().authenticated());
		
		//http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		
		http.formLogin(form-> form.loginPage("/login"));
		
		http.logout(logout -> logout.logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies ("JSESSIONID"));
		
		http.sessionManagement (session -> session.maximumSessions(1).maxSessionsPreventsLogin(false).expiredUrl("/login?error=2"));
		
		http.httpBasic();
		return http.build();
	}
	
}


