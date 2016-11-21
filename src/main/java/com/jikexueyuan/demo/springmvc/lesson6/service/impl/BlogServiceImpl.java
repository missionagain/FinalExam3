package com.jikexueyuan.demo.springmvc.lesson6.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jikexueyuan.demo.springmvc.lesson6.dao.BlogDao;
import com.jikexueyuan.demo.springmvc.lesson6.entity.Blog;
import com.jikexueyuan.demo.springmvc.lesson6.service.BlogService;


@Service
public class BlogServiceImpl implements BlogService {
	
	@Resource
	BlogDao blogDao;
	
//	//@Transactional
//	public void saveBlog(String blogTitle, String blogContent) {
	
//		blogDao.Save(blogTitle,blogContent);		
//	}

	
	public void saveBlog(Blog blog) {
		
		blogDao.Save(blog);
		
	}
	
	public Blog selectBlog(int blogId) {	
		
		Blog blog=blogDao.select(blogId);
		return blog;
	}



	

}
