package com.javaex.dao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BlogVo;

@Repository
public class BlogDao {
	
	@Autowired
	private SqlSession sqlSession;
	//유저 가입시 새 블로그
	public int insertBlog(BlogVo blogVo) {
		
		return sqlSession.insert("blog.insertblog",blogVo);
	}
	
	//블로그 접속
	public BlogVo joinBlog(String id) {
		return sqlSession.selectOne("blog.selectblog",id);
	}
	
	public int UpdateBasicBlog(BlogVo blogVo) {
		return sqlSession.update("blog.updateblog",blogVo);
	}

}
