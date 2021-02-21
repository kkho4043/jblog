package com.javaex.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PostVo;

@Repository
public class BlogPostDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//유저 가입시 새 블로그
	public int blogwriteinsert(PostVo postVo) {
		System.out.println("write postVo :" + postVo);
		return sqlSession.insert("post.insertpost",postVo);
	}
	
	public List<PostVo> selectListPost(Map<String, Object> sMap) {
	
		
		List<PostVo> pList = sqlSession.selectList("post.selectListPost",sMap);
		
		
		return pList;
	}
	public PostVo selectPost(Map<String, Object> sMap) {
		return sqlSession.selectOne("post.selectPost",sMap);
	}
	
	
}
