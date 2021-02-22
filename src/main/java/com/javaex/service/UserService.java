package com.javaex.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogMainDao;
import com.javaex.dao.UserDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.UserVo;

@Service("/UserService")
public class UserService {
	
	@Autowired
	BlogMainDao blogDao;
	
	@Autowired
	UserDao userDao;
	
	
	//회원가입
	public int join(UserVo userVo) {
		
		int a = userDao.insert(userVo);	
		BlogVo blogVo = new BlogVo();
		
		blogVo.setId(userVo.getId());
		blogVo.setBlogTitle(userVo.getId()+"의 블로그입니다.");
		blogVo.setLogoFile("/assets/images/spring-logo.jpg");
		blogDao.insertBlog(blogVo); 
		
		return 1;
			
	}
	
	public UserVo login(UserVo userVo) {
		return userDao.selectUser(userVo);
	}
	
	public void logout(HttpSession session) {
		session.removeAttribute("authUser");
	}
	
}
