package com.javaex.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.BlogCateService;
import com.javaex.service.BlogMainService;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CateVo;

@Controller
@RequestMapping("/")
public class BlogCateController {

	@Autowired
	private BlogCateService blogCateService;
	
	@Autowired
	private BlogMainService blogMainService;

	// 블로그 어드민 카테고리
	@RequestMapping(value = "{id}/admin/cate", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdmincate(@PathVariable("id") String id, Model model, HttpSession session) {
		System.out.println("/blogController/" + id + "/cate-----------------");

		BlogVo blogVo = blogMainService.blogadmin(id, session);
		if (blogVo != null) {
			model.addAttribute("blogVo", blogVo);
			return "blog/admin/blog-admin-cate";
		} else {
			return "redirect:/user/loginForm";
		}
	}

	// 어드민 카테고리 리스트
	@ResponseBody
	@RequestMapping(value = "{id}/admin/cateList", method = { RequestMethod.GET, RequestMethod.POST })
	public List<CateVo> blogAdmincateList(@PathVariable("id") String id, Model model, HttpSession session) {
		System.out.println("/blogController/" + id + "/cate-----------------");
		List<CateVo> cate = blogCateService.blogcate(id, session);
		System.out.println(cate.toString());
		return cate;
	}

	// 블로그 카테로기 추가
	@ResponseBody
	@RequestMapping(value = "{id}/admin/cateadd", method = { RequestMethod.GET, RequestMethod.POST })
	public CateVo blogAdmincateadd(@PathVariable("id") String id, HttpSession session,
			@ModelAttribute("CateVo") CateVo cateVo, @RequestParam("description") String description) {

		System.out.println("/blogController/" + id + "/cateadd-----------------");
		System.out.println(cateVo);
		return blogCateService.blogcateadd(id, session, cateVo);
	}

	@ResponseBody
	@RequestMapping(value = "{id}/admin/catedelete", method = { RequestMethod.GET, RequestMethod.POST })
	public int blogAdmincatedelete(@PathVariable("id") String id, HttpSession session,
			@ModelAttribute("CateVo") CateVo cateVo) {

		System.out.println("/blogController/" + id + "/cateadd-----------------");
		System.out.println(cateVo.toString());
		return blogCateService.blogcatedelete(id, session, cateVo);
	}

}
