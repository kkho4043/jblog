package com.javaex.dao;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.CateVo;

@Repository
public class BlogCateDao {
	
	@Autowired
	private SqlSession sqlSession;
	//유저 가입시 새 블로그
	public int insertCate(CateVo cateVo) {
		return sqlSession.insert("cate.insertcate",cateVo);
	}
	public List<CateVo> selectListCate(String id) {
		return sqlSession.selectList("cate.selectListcate",id);
	}
	
	public CateVo selectCatelast(String id) {
		return sqlSession.selectOne("cate.selectcatelast",id);
	}
	
	public int catedelete(int no) {
		return sqlSession.delete("cate.catedelete",no);
	}
	
}
