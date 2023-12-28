package com.example.park.myrestfulservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

	@GetMapping("/login")
    public String login(HttpServletRequest request){

		request.getSession().setAttribute("member", "ok");
		
		System.out.println("ok");
        return "null";
    }
	
	@GetMapping("/loginOut")
    public String loginOut(HttpServletRequest request){

		request.getSession().invalidate();
		
        return "null";
    }
	
}
