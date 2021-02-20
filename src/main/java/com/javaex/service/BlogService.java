package com.javaex.service;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.dao.BlogDao;
import com.javaex.vo.BlogVo;
import com.javaex.vo.UserVo;

@Service("/BlogService")
public class BlogService {

	@Autowired
	BlogDao blogDao;

	// 블로그 접속
	public BlogVo blogjoin(String id) {
		return blogDao.joinBlog(id);
	}

	// 내 블로그 어드민
	public BlogVo blogadmin(String id,HttpSession session) {
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if (userVo == null || !(id.equals(userVo.getId()))  || id == null) {
			return null;
		} else {
			return blogDao.joinBlog(userVo.getId());
		}
	}
	
	public int blogadminModify(String id,HttpSession session,MultipartFile file,String blogTitle) {
		
		//세션에 아이디와 아이디가 같으면 수행한다
		//
		//1.파일을 받아 저장하고 위치를 저장한다
		//타이틀과 파일저장한 위치를 받아 db에 저장한다
		
		UserVo userVo = (UserVo) session.getAttribute("authUser");
		if (userVo == null || !(id.equals(userVo.getId()))  || id == null) {
			return 0;
		} else {
			String saveName = "";
			if(file.getSize() > 0) {
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
			
			
			
			BlogVo blogvo = new BlogVo(userVo.getId(),blogTitle,saveName);
				
			return blogDao.UpdateBasicBlog(blogvo);
		}
	}
}
