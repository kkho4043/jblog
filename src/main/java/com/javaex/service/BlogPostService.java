package com.javaex.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogMainDao;
import com.javaex.dao.BlogPostDao;
import com.javaex.dao.BlogCateDao;
import com.javaex.vo.PostVo;
import com.javaex.vo.UserVo;

@Service("/BlogWriteService")
public class BlogPostService {

	@Autowired
	BlogMainDao blogMainDao;

	@Autowired
	BlogCateDao blogCateDao;

	@Autowired
	BlogPostDao blogPostDao;

	public Map<String, Object> blogPost(String id, HttpSession session) {

		if (equlsIdSession(id, session)) {
			Map<String, Object> bMap = new HashMap<String, Object>();
			bMap.put("blogVo", blogMainDao.joinBlog(id));
			bMap.put("cList", blogCateDao.selectListCate(id));
			return bMap;
		}
		return null;
	}

	public int blogPostinsert(String id, HttpSession session, int cateNo, PostVo postVo) {
		if (equlsIdSession(id, session)) {
			postVo.setCateNo(cateNo);
			return blogPostDao.blogwriteinsert(postVo);
		}
		return 0;
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
