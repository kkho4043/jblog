package com.javaex.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogMainDao;
import com.javaex.dao.BlogPostDao;
import com.javaex.dao.BlogCateDao;
import com.javaex.vo.CateVo;
import com.javaex.vo.UserVo;

@Service("/BlogCateService")
public class BlogCateService {

	@Autowired
	BlogMainDao blogMainDao;

	@Autowired
	BlogCateDao blogCateDao;
	
	@Autowired
	BlogPostDao blogWriteDao;

	


	// 카테고리 리스트
	public List<CateVo> blogcate(String id, HttpSession session) {

		if (equlsIdSession(id, session)) {

			return blogCateDao.selectListCate(id);
		} else {
			return null;
		}
	}

	
	// 카테고리 추가
	public CateVo blogcateadd(String id, HttpSession session, CateVo cateVo) {

		if (equlsIdSession(id, session)) {

			cateVo.setId(id);

			if (blogCateDao.insertCate(cateVo) == 1) {
				return blogCateDao.selectCatelast(id);
			}

		}

		return null;
	}

	public int blogcatedelete(String id, HttpSession session, CateVo cateVo) {

		if (equlsIdSession(id, session)) {
			
			if(cateVo.getAmount() == 0) {
				return blogCateDao.catedelete(cateVo.getCateNo());	
			}
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
