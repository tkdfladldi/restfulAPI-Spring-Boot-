package com.example.park.myrestfulservice.config.securityConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import net.minidev.json.JSONObject;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private static final String AJAX_HEADER_KEY = "X-Requested-With";
	private static final String AJAX_X_REQUESTED_WITH= "XMLHttpRequest";
	private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,AuthenticationException exception) throws IOException, ServletException {
		int errorCode;
		if (exception instanceof BadCredentialsException) { //아이디, 비밀번호, 핸드폰 번호 입력 에러
			errorCode = 1;
		} else if (exception instanceof SessionAuthenticationException) { // 중복 로그인
			errorCode = 2;
		} else { // 기타 에러
			errorCode = 9;
		}
		
		logger.error("Login fail : {}", exception.getMessage());
		if (isAjax(request)) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("code", errorCode);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE); response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.setStatus(HttpServletResponse.SC_OK); response.getWriter().write(jsonObj.toJSONString());
			response.getWriter().flush();
		}else {
			String location = String.format("%s/login?error-%d", request.getContextPath(), errorCode);
			response.sendRedirect(location);
		}
	}
	
	private boolean isAjax (HttpServletRequest request) {
		String ajaxHeader = request.getHeader (AJAX_HEADER_KEY);
		return (ajaxHeader != null && AJAX_X_REQUESTED_WITH.equalsIgnoreCase(ajaxHeader));
	}	
	
}



	
