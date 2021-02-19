package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BlogDao;
import com.javaex.dao.UserDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.UserVo;

@Service("/UserService")
public class UserService {
	
	@Autowired
	BlogDao blogDao;
	
	@Autowired
	UserDao userDao;
	
	
	//회원가입
	public int join(UserVo userVo) {
		
		int a = userDao.insert(userVo);	
		BlogVo blogVo = new BlogVo();
		
		blogVo.setId(userVo.getId());
		blogVo.setBlogTitle(userVo.getId()+"의 블로그입니다.");
		
		blogDao.insertBlog(blogVo); 
		
		return 1;
			
	}
	
	public UserVo login(UserVo userVo) {
		
		return userDao.selectUser(userVo);
		
	}
	
}
