package com.javaex.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.BlogBasicService;
import com.javaex.service.BlogMainService;
import com.javaex.vo.BlogVo;

@Controller
@RequestMapping("/")
public class BlogBasicController {

	@Autowired
	private BlogBasicService blogBasicService;
	
	@Autowired
	private BlogMainService blogMainService;

	@RequestMapping(value = "{id}/admin/basic", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdminbasic(@PathVariable("id") String id, Model model,HttpSession session) {
		System.out.println("/blogController/" + id + "/basic-----------------");
		BlogVo blogVo = blogMainService.blogadmin(id,session);
		if (blogVo != null) {
			model.addAttribute("blogVo", blogVo);
			return "blog/admin/blog-admin-basic";
		} else {
			return "redirect:/user/loginForm";
		}
		
	}
	//블로그 기본 설정
	@RequestMapping(value = "{id}/admin/basicModify", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdminbasicModify(@PathVariable("id") String id,HttpSession session,
									   @RequestParam("blogTitle") String blogTitle, 
									   @RequestParam(value = "file",required = false) MultipartFile file) {
		System.out.println("/blogController/" + id + "/basicmodifi-----------------");
		
		int flag = blogBasicService.blogbasicModify(id,session,file,blogTitle);
		
		try {
			id = URLEncoder.encode(id, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String view = "redirect:/"+id+"/admin/basic";
		if(flag == 1) {
			
			return view;
		}else {
			return "error/403";
		}
	}
	
}
