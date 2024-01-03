package com.example.park.myrestfulservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class LoginController {

	@GetMapping("/home")
    public String home(HttpServletRequest request){
        return "home";
    }
	
	@GetMapping("/login")
    public String login(HttpServletRequest request){
        return "login";
    }
	
	@GetMapping("/loginOut")
    public String loginOut(HttpServletRequest request){

		request.getSession().invalidate();
		
        return "null";
    }
	
}
