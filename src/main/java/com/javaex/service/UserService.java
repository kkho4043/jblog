package com.javaex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@Service("/UserService")
public class UserService {
	
	@Autowired
	UserDao userDao;
	
	//회원가입
	public int join(UserVo userVo) {
		return userDao.insert(userVo);		
	}
	
	public UserVo login(UserVo userVo) {
		return userDao.selectUser(userVo);
	}
	
	

}
