package com.jikexueyuan.demo.springmvc.lesson6.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jikexueyuan.demo.springmvc.lesson6.entity.Blog;

public interface BlogDao {
	
//	@Insert("insert into blog(blogId,blogTitle,blogContent) values(null,#{0}, #{1})")
//	public void Save(@Param("blogTitle")String blogTitle,@Param("blogContent")String blogContent);
	
	@Insert("insert into blog(blogTitle,blogContent) values(#{blogTitle}, #{blogContent})")
	@Options(useGeneratedKeys=true,keyProperty="uid")
	public void Save(Blog blog);
	
	@Select("select * from blog where blogId=#{blogId}")
	public Blog select(int blogId);

}
