package com.javaex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	
	//@Autowired
	//private GalleryService galleryService;

	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public String main(Model model) {
		System.out.println("/jblog/main-----------------");
		return "main/index";
	}
	
	
}
