package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.BlogDao;
import com.javaex.dao.BlogWriteDao;
import com.javaex.dao.CateDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.CateVo;
import com.javaex.vo.PostVo;
import com.javaex.vo.UserVo;

@Service("/BlogService")
public class BlogService {

	@Autowired
	BlogDao blogDao;

	@Autowired
	CateDao cateDao;
	
	@Autowired
	BlogWriteDao blogWriteDao;

	// 블로그 접속
	public BlogVo blogjoin(String id) {
		return blogDao.joinBlog(id);
	}

	// 내 블로그 어드민
	public BlogVo blogbasic(String id, HttpSession session) {

		if (equlsIdSession(id, session)) {
			return blogDao.joinBlog(id);
		} else {
			return null;
		}
	}

	// 카테고리 리스트
	public List<CateVo> blogcate(String id, HttpSession session) {

		if (equlsIdSession(id, session)) {

			return cateDao.selectListCate(id);
		} else {
			return null;
		}
	}

	public int blogbasicModify(String id, HttpSession session, MultipartFile file, String blogTitle) {

		// 세션에 아이디와 아이디가 같으면 수행한다
		//
		// 1.파일을 받아 저장하고 위치를 저장한다
		// 타이틀과 파일저장한 위치를 받아 db에 저장한다

		if (equlsIdSession(id, session)) {
			String saveName = "";
			if (file.getSize() > 0) {
				// 오리지널 파일이름
				String orgName = file.getOriginalFilename();
				// 확장
				String exName = orgName.substring(orgName.lastIndexOf("."));
				// 서버저장용 이름(서버컴퓨터.)
				saveName = System.currentTimeMillis() + UUID.randomUUID().toString() + exName;
				// 저장위치
				String saveDir = "C:\\javaStudy\\upload";

				// 저장경로
				String filePath = saveDir + "\\" + saveName;
				System.out.println("filePath");
				System.out.println(saveDir);
				// 저장경로에 저장하기
				try {
					byte[] fileDate = file.getBytes();
					OutputStream out = new FileOutputStream(filePath);
					BufferedOutputStream bos = new BufferedOutputStream(out);

					bos.write(fileDate);
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			BlogVo blogvo = new BlogVo(id, blogTitle, saveName);
			return blogDao.UpdateBasicBlog(blogvo);
		} else {
			return 0;
		}
	}

	// 카테고리 추가
	public CateVo blogcateadd(String id, HttpSession session, CateVo cateVo) {

		if (equlsIdSession(id, session)) {

			cateVo.setId(id);

			if (cateDao.insertCate(cateVo) == 1) {
				return cateDao.selectCatelast(id);
			}

		}

		return null;
	}

	public int blogcatedelete(String id, HttpSession session, CateVo cateVo) {

		if (equlsIdSession(id, session)) {
			
			if(cateVo.getAmount() == 0) {
				return cateDao.catedelete(cateVo.getCateNo());	
			}
		}
		return 0;
	}
	
	public Map<String,Object> blogwrite(String id, HttpSession session) {

		if (equlsIdSession(id, session)) {
			Map <String ,Object> bMap = new HashMap<String ,Object>();
			bMap.put("blogVo",blogDao.joinBlog(id));
			bMap.put("cList",cateDao.selectListCate(id));
			return bMap;
		}
		return null;
	}
	
	public int blogwriteinsert(String id, HttpSession session,int cateNo,PostVo postVo) {
		if (equlsIdSession(id, session)) {
			postVo.setCateNo(cateNo);
			return blogWriteDao.blogwriteinsert(postVo); 
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
