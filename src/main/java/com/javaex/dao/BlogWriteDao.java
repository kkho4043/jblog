package com.javaex.dao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PostVo;

@Repository
public class BlogWriteDao {
	
	@Autowired
	private SqlSession sqlSession;
	//유저 가입시 새 블로그
	public int blogwriteinsert(PostVo postVo) {
		System.out.println("write postVo :" + postVo);
		return sqlSession.insert("write.insertwrite",postVo);
	}
	
	
}
