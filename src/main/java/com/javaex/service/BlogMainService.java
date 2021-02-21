package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogCateDao;
import com.javaex.dao.BlogMainDao;
import com.javaex.dao.BlogPostDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.PostVo;
import com.javaex.vo.UserVo;

@Service("/BlogMainService")
public class BlogMainService {

	@Autowired
	BlogMainDao blogMainDao;

	@Autowired
	BlogCateDao blogCateDao;

	@Autowired
	BlogPostDao blogPostDao;
	
	

	// 블로그 접속
	
	public BlogVo blogjoin(String id) {
		
		return blogMainDao.joinBlog(id);
	}
	
	public Map<String, Object> blogMainList(String id,int cateNo,int postNo) {
		
		Map<String, Object> bMap = new HashMap<String, Object>();
		
		bMap.put("blogVo", blogjoin(id));
		
		bMap.put("cList", blogCateDao.selectListCate(id));
		
		Map<String, Object> sMap = new HashMap<String, Object>();
		sMap.put("id",id);
		sMap.put("cateNo",cateNo);
		sMap.put("postNo",postNo);
		
		bMap.put("pList", blogPostDao.selectListPost(sMap));
		
		bMap.put("postVo", blogPostDao.selectPost(sMap));
		
		return bMap;
	}

	// 내 블로그 어드민
	public BlogVo blogadmin(String id, HttpSession session) {

		if (equlsIdSession(id, session)) {
			return blogMainDao.joinBlog(id);
		} else {
			return null;
		}
	}

	// 블로그 사용자가 자신이 맞는지 판단해줌.
	public boolean equlsIdSession(String id, HttpSession session) {

		UserVo userVo = (UserVo) session.getAttribute("authUser");
		System.out.println("userVo = " + userVo);
		System.out.println("id = " + id);
		if (userVo != null && (id.equals(userVo.getId())) && id != null) {
			System.out.println("본인.");
			return true;
		} else {
			return false;
		}

	}

}
