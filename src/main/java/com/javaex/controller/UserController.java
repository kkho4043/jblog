package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/loginForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm() {
		System.out.println("/UserController/loginForm-----------------");
		return "user/loginForm";
	}
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@ModelAttribute UserVo userVo , HttpSession session) {
		System.out.println("/UserController/login-----------------");
		UserVo authUser = userService.login(userVo);
		// 성공
		if (authUser != null) {
			
			System.out.println("login ok:" + authUser);
			session.setAttribute("authUser", authUser);

			return "redirect:/";
		}
		// 실패
		else {
			System.out.println("login fales:" + authUser);
			return "redirect:/user/loginForm?result=fail";
		}
	}
	
	
	@RequestMapping(value = "/joinForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinForm() {
		System.out.println("/UserController/joinForm-----------------");
		return "user/joinForm";
	}
	
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public String join(@ModelAttribute UserVo userVo) {
		System.out.println("/UserController/join-----------------");
		System.out.println("UserVo : " + userVo);
		
		userService.join(userVo);
			
		return "user/joinSuccess";
		
	}
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpSession session) {
		System.out.println("/UserController/logout-----------------");
		
		
		userService.logout(session);
			
		return "redirect:/";
		
	}
	
	
}
