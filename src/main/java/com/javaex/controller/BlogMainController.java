package com.javaex.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.BlogMainService;
import com.javaex.vo.BlogVo;

@Controller
@RequestMapping("/")
public class BlogMainController {

	@Autowired
	private BlogMainService blogService;

	@RequestMapping(value = "{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String blog(@PathVariable("id") String id, Model model, HttpSession session,
			@RequestParam(value = "cateNo", required = false ,defaultValue = "0") int cateNo,
			@RequestParam(value = "postNo", required = false ,defaultValue = "0") int postNo) {
		System.out.println("/blogController/" + id + "/main-----------------");
		Map<String, Object> bMap = blogService.blogMainList(id, cateNo, postNo);


		 if (bMap.get("blogVo") != null){ 
			 model.addAttribute("blogVo",bMap.get("blogVo"));
			 model.addAttribute("cList",bMap.get("cList"));
			 model.addAttribute("pList",bMap.get("pList"));
			 model.addAttribute("postVo",bMap.get("postVo"));
			 return "blog/blog-main";
		 } else {
			 return "error/403";
		 }
		 
		
	}

}
