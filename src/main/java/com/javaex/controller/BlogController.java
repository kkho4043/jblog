package com.javaex.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javaex.service.BlogService;
import com.javaex.vo.BlogVo;

@Controller
@RequestMapping("/")
public class BlogController {

	@Autowired
	private BlogService blogService;

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

	@RequestMapping(value = "{id}/admin/basic", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdminbasic(@PathVariable("id") String id, Model model,HttpSession session) {
		System.out.println("/blogController/" + id + "/basic-----------------");
		BlogVo blogVo = blogService.blogadmin(id,session);
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
									   @RequestParam(value = "blogTitle") String blogTitle, 
									   @RequestParam(value = "file",required = false) MultipartFile file,
									   HttpServletRequest request,
					                   RedirectAttributes redirectAttributes) {
		System.out.println("/blogController/" + id + "/basicmodifi-----------------");
		
		int flag = blogService.blogadminModify(id,session,file,blogTitle);
		
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
	
	@RequestMapping(value = "{id}/admin/cate", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdmincate(@PathVariable("id") String id, Model model) {
		System.out.println("/blogController/" + id + "/cate-----------------");
		model.addAttribute("id", id);
		return "blog/admin/blog-admin-cate";
	}

	@RequestMapping(value = "/{id}/admin/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdminwrite(@PathVariable("id") String id, Model model) {
		System.out.println("/blogController/" + id + "/write-----------------");
		model.addAttribute("id", id);
		return "blog/admin/blog-admin-write";
	}

}
