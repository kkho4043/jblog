package com.javaex.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.BlogService;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CateVo;
import com.javaex.vo.PostVo;

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
		BlogVo blogVo = blogService.blogbasic(id,session);
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
		
		int flag = blogService.blogbasicModify(id,session,file,blogTitle);
		
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
	
	//블로그 어드민 카테고리
	@RequestMapping(value = "{id}/admin/cate", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdmincate(@PathVariable("id") String id, Model model,HttpSession session) {
		System.out.println("/blogController/" + id + "/cate-----------------");
	
		BlogVo blogVo = blogService.blogbasic(id,session);
		if (blogVo != null) {
			model.addAttribute("blogVo", blogVo);
			return "blog/admin/blog-admin-cate";
		} else {
			return "redirect:/user/loginForm";
		}
	}
	
	//어드민 카테고리 리스트
	@ResponseBody
	@RequestMapping(value = "{id}/admin/cateList", method = { RequestMethod.GET, RequestMethod.POST })
	public List<CateVo> blogAdmincateList(@PathVariable("id") String id, Model model,HttpSession session) {
		System.out.println("/blogController/" + id + "/cate-----------------");
		List<CateVo> cate = blogService.blogcate(id, session);
		System.out.println(cate.toString());
		return cate;
	}
	
	//블로그 카테로기 추가
	@ResponseBody
	@RequestMapping(value = "{id}/admin/cateadd", method = { RequestMethod.GET, RequestMethod.POST })
	public CateVo blogAdmincateadd(@PathVariable("id") String id,HttpSession session,
								@ModelAttribute("CateVo") CateVo cateVo,
								@RequestParam("description") String description){
		
		System.out.println("/blogController/" + id + "/cateadd-----------------");
		System.out.println(cateVo);
		return blogService.blogcateadd(id,session,cateVo);
	}
	
	@ResponseBody
	@RequestMapping(value = "{id}/admin/catedelete", method = { RequestMethod.GET, RequestMethod.POST })
	public int blogAdmincatedelete(@PathVariable("id") String id,HttpSession session,
								   @ModelAttribute("CateVo") CateVo cateVo){
		
		System.out.println("/blogController/" + id + "/cateadd-----------------");
		System.out.println(cateVo.toString());
		return blogService.blogcatedelete(id, session,cateVo);
	}
	
	//블로그 글쓰기
	@RequestMapping(value = "/{id}/admin/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdminwrite(@PathVariable("id") String id,HttpSession session,Model model) {
		System.out.println("/blogController/" + id + "/write-----------------");
		
		Map<String,Object> bMap =  blogService.blogwrite(id,session);
		
		if (bMap != null) {
			model.addAttribute("cList",bMap.get("cList"));
			model.addAttribute("blogVo",bMap.get("blogVo"));
			return "blog/admin/blog-admin-write";
		} else {
			return "redirect:/user/loginForm";
		}
		
	}
	
	@RequestMapping(value = "/{id}/admin/writeinsert", method = { RequestMethod.GET, RequestMethod.POST })
	public String blogAdminwriteinsert(@PathVariable("id") String id,HttpSession session,
									   @RequestParam("cateNo") int cateNo,
									   @ModelAttribute("postVo") PostVo postVo) {
		System.out.println("/blogController/" + id + "/write-----------------");
		
		
		
		if (blogService.blogwriteinsert(id, session, cateNo, postVo) == 1) {
			
			try {
				id = URLEncoder.encode(id, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return "redirect:/"+id+"/admin/write";
		} else {
			return "error/403";
		}
		
	}

}
