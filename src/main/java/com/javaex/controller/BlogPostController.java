package com.javaex.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BlogPostService;
import com.javaex.vo.PostVo;

@Controller
@RequestMapping("/")
public class BlogPostController {

	@Autowired
	private BlogPostService blogPostService;

	// 블로그 글쓰기
	@RequestMapping(value = "/{id}/admin/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdminwrite(@PathVariable("id") String id, HttpSession session, Model model) {
		System.out.println("/blogController/" + id + "/write-----------------");

		Map<String, Object> bMap = blogPostService.blogPost(id, session);

		if (bMap != null) {
			model.addAttribute("cList", bMap.get("cList"));
			model.addAttribute("blogVo", bMap.get("blogVo"));
			return "blog/admin/blog-admin-write";
		} else {
			return "redirect:/user/loginForm";
		}

	}

	@RequestMapping(value = "/{id}/admin/writeinsert", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdminwriteinsert(@PathVariable("id") String id, HttpSession session,
			@RequestParam("cateNo") int cateNo, @ModelAttribute("postVo") PostVo postVo) {
		System.out.println("/blogController/" + id + "/write-----------------");

		if (blogPostService.blogPostinsert(id, session, cateNo, postVo) == 1) {

			try {
				id = URLEncoder.encode(id, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "redirect:/" + id + "/admin/write";
		} else {
			return "error/403";
		}

	}

}
