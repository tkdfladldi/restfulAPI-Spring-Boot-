package com.example.park.myrestfulservice.config.securityConfig;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class WebConfig {
	
	//Spring Security에서 특정 요청 경로에 대한 보안 검사를 비활성화하여 해당 리소스에 대한 자유로운 접근을 허용
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
						   .antMatchers("/css/**", "/js/**", "/images/**", "/libs/**", "/error");
	}
	
//	@Bean
//	public FilterRegistrationBean<XssEscapeServletFilter> xssEscapeServletFilter() {
//		FilterRegistrationBean<XssEscapeServletFilter> registrationBean = new FilterRegistrationBean<>();
//		registrationBean.setFilter(new XssEscapeServletFilter());
//		registrationBean.setOrder (1);
//		registrationBean.addUrlPatterns ("/*");
//		return registrationBean;
//	}
}
