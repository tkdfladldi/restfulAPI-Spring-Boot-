package com.example.park.myrestfulservice.config.securityConfig;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.park.myrestfulservice.listener.SessionListener;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	
//	@Autowired
//	private AdminMapper adminMapper;
//	@Autowired
//	private GroupMapper groupMapper; 
//	@Autowired
//	private ApiService apiService;
//	@Autowired
//	private MobileAuthService mobileAuthService; 
	@Autowired
	private SessionListener sessionListener;
	
	
	@Override
	public Authentication authenticate (Authentication authentication) throws AuthenticationException { 
		String username = String.valueOf(authentication.getPrincipal());
		String password= String.valueOf(authentication.getCredentials());
		String phoneNo= null;
		
		if (Objects.isNull(username) || username.isEmpty()) { 
			throw new UsernameNotFoundException("존재하지 않는 계정입니다.");
		}
		if (Objects.isNull(password) || password.isEmpty()) {
			throw new BadCredentialsException("패스워드가 입력되지 않았습니다.");
		}
		
		
		if (authentication instanceof CustomAuthenticationToken) { 
			CustomAuthenticationToken authToken = (CustomAuthenticationToken) authentication;
			phoneNo = authToken.getPhoneNo();
			
			if (Objects.isNull(phoneNo) | phoneNo.isEmpty()) {
				throw new BadCredentialsException("핸드폰 번호가 입력되지 않았습니다.");
			}
		}
		
		logger.info("-----Login: {}", username);
		
		//AdminDTO adminDto  = adminMapper.findAdminById(username);
		
//		if (Objects.isNull(adminDto) || adminDto.getDeleted().equals("Y")) { 
//			throw new UsernameNotFoundException("핸드폰 번호가 입력되지 않았습니다.");
//		}
		
//		if (adminDto.getActive().equals("N")) {
//			throw new BadCredentialsException("비활성화된 계정입니다.");
//		}
		
//		logger.debug("# ApiService 20: {}", apiService.isEnable()); 
//		logger.debug("# Mobile : ()", mobileAuthService.isEnable());
		
//		if (apiService.isEnable()) {
//			try {
//				String userId ="SH".concat(username);
//				//String token = apiService.getUserToken(userId);
//				//boolean check = apiService.checkUserAuth(token, password);
//				
//				if (!check) {
//					throw new BadCredentialsException("통합로그인 인증에 실패하였습니다.");
//				}
//			}catch (ApiException ex) {
//				throw new BadCredentialsException("통합로그인 인증에 실패하였습니다.");
//			}
//			
//			if (mobileAuthService.isEnable() && phoneNo != null) { 
//				boolean check = mobileAuthService.authUser(phoneNo);
//			
//			}
//			
//			if (!check) {
//				throw new BadCredentialsException("모바일 인증에 실패하였습니다.");
//			}
//		}
		
		sessionListener.checkSession (username);
		
		//List<AdminMenu> menus  = groupMapper.findMappingMenusByRoleGroupId(adminDto.getRoleGroupId()); 
		
		//List<GrantedAuthority> authorities = menus.stream().map(r -> new SimpleGrantedAuthority (r.getRoleName())).collect (Collectors.toList());
		//return new CustomAuthenticationToken(adminDto.getNo(), adminDto.getPassword(), authorities);
		
		GrantedAuthority authorities = new SimpleGrantedAuthority("USER");
		
		List<GrantedAuthority> authoritieList = new ArrayList<>();
		authoritieList.add(authorities);
		return new CustomAuthenticationToken(username, password , authoritieList);
		
	}
	
	@Override
	public boolean supports (Class<?> authentication) {
		return authentication.equals (CustomAuthenticationToken.class);
	}
}
	


