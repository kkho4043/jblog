package com.javaex.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogCateDao;
import com.javaex.dao.BlogMainDao;
import com.javaex.dao.BlogWriteDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.UserVo;

@Service("/BlogMainService")
public class BlogMainService {

	@Autowired
	BlogMainDao blogDao;

	@Autowired
	BlogCateDao cateDao;

	@Autowired
	BlogWriteDao blogWriteDao;

	// 블로그 접속
	public BlogVo blogjoin(String id) {
		return blogDao.joinBlog(id);
	}

	// 내 블로그 어드민
	public BlogVo blogadmin(String id, HttpSession session) {

		if (equlsIdSession(id, session)) {
			return blogDao.joinBlog(id);
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
