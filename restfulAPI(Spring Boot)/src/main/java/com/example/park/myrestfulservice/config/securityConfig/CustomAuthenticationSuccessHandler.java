package com.example.park.myrestfulservice.config.securityConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import net.minidev.json.JSONObject;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private static final String AJAX_HEADER_KEY = "X-Requested-With";
	private static final String AJAX_X_REQUESTED_WITH = "XMLHttpRequest";
	private final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
	
//	@Value("$(server.servlet.session.timeout}")
//	private int sessionTime;
	
//	@Autowired
//	private AdminMapper adminMapper;
	
//	@Autowired
//	private GroupMapper groupMapper
	
	private final String redirectLocation;
	private final RequestCache requestCache = new HttpSessionRequestCache();
	private final RedirectStrategy redirectStratgy = new DefaultRedirectStrategy();
	
	public CustomAuthenticationSuccessHandler(String location) {
		this.redirectLocation = location;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException { 
		Integer adminNo= (Integer) authentication.getPrincipal();
		//AdminDTO adminDto = adminMapper findAdminByNo (adminNo);
		//Integer groupId = adminDto.getRoleGroupId();
		//final List<AdminMenu> menus = roleGroupMapper.findMappingMenusByRoleGroupId (roleGroupId); 
		//final List<MenuVO> menuList new ArrayList<>(10);
		//menus.stream().filter(m-> m.getDepth() == 1).forEach(m1 -> { List<AdminMenu> subMenus menus.stream().filter(m2 > Objects.equals(m2.getParentMenuId(), m1.getId())) .collect(Collectors.toList());
		//	menulist.add(new MenuVO (m1, subMenus));
		//});
		
		
		//HttpSession session =request.getSession(); 
		//session.setMaxInactiveInterval(sessionTime); 
		//session.setAttribute("menus", menulist); 
		//session.setAttribute("admin", adminDto);
		//logger.info("# Session -> MaxInactive Interval: sec", session.getMaxInactive Interval());
		
		if (isAjax(request)) {
			String targetUrl = getTargetURL(request, response);
			if (targetUrl.startsWith("/")) {
				targetUrl= request.getContextPath().concat(targetUrl);
			}
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("code", 0);
			jsonObj.put("url", targetUrl);
			
			response.setContentType (MediaType.APPLICATION_JSON_VALUE); response.setCharacterEncoding (StandardCharsets.UTF_8.name());
			response.setStatus (HttpServletResponse.SC_OK);
			response.getWriter().write(jsonObj.toJSONString());
			response.getWriter().flush();
		} else {
				resultRedirectStrategy(request, response);
		}
		
	}
	private String getTargetURL(HttpServletRequest request, HttpServletResponse response) { 
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String targetUrl= (savedRequest != null)? savedRequest.getRedirectUrl(): redirectLocation;
		return targetUrl;
	}
	private void resultRedirectStrategy (HttpServletRequest request, HttpServletResponse response) throws IOException { 
		String targetUrl= getTargetURL(request, response);
		redirectStratgy.sendRedirect(request, response, targetUrl);
	}
	private boolean isAjax(HttpServletRequest request) {
		String ajaxHeader = request.getHeader (AJAX_HEADER_KEY);
		return (ajaxHeader != null && AJAX_X_REQUESTED_WITH.equalsIgnoreCase(ajaxHeader));
	}

}

