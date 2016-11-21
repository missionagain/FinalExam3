package com.jikexueyuan.demo.springmvc.lesson6.service;

import com.jikexueyuan.demo.springmvc.lesson6.entity.Blog;

public interface BlogService {

//	public void saveBlog(String blogTitle,String blogContent);
	
	public void saveBlog(Blog blog);
	
	public Blog selectBlog(int blogId);
}
