package com.javaex.dao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BlogVo;

@Repository
public class BlogBasicDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int UpdateBasicBlog(BlogVo blogVo) {
		return sqlSession.update("blog.updateblog",blogVo);
	}

}
