package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.service.BlogMainService;
import com.javaex.vo.BlogVo;

@Controller
@RequestMapping("/")
public class BlogMainController {

	@Autowired
	private BlogMainService blogService;

	@RequestMapping(value = "{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String blog(@PathVariable("id") String id, Model model,HttpSession session) {
		System.out.println("/blogController/" + id + "/main-----------------");
		BlogVo blogVo = blogService.blogjoin(id);
		if (blogVo != null) {
			model.addAttribute("blogVo", blogVo);
			return "blog/blog-main";
		} else {
			return "error/403";
		}
	}

}
