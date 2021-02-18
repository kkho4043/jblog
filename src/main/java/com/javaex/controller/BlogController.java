package com.javaex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class BlogController {
	
	//@Autowired
	//private UserService userService;

	@RequestMapping(value = "{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String blog(@PathVariable("id") String id,Model model) {
		System.out.println("/blogController/id-----------------");
		model.addAttribute("userName", id);
		return "blog/blog-main";
	}
	
	
	
}
