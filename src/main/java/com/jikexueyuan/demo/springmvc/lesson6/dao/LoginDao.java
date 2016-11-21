package com.jikexueyuan.demo.springmvc.lesson6.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jikexueyuan.demo.springmvc.lesson6.entity.Blog;
import com.jikexueyuan.demo.springmvc.lesson6.entity.User;
import com.jikexueyuan.demo.springmvc.lesson6.entity.UserInfo;

public interface LoginDao {
	@Select("select * from person where userName=#{userName} and Password=#{userPassword}")
	public User login(@Param("userName")String userName,@Param("userPassword")String userPassword);
	
	
	@Select("select * from person where userName=#{userName} and Password=#{userPassword}")
	public User loginUser(User user);
	
	
	@Select("select * from userinfo where userId=#{userId}")
	public User SelectbyId(Integer userId);
	
	@Insert("insert into person(userName,Password) values(#{userName}, #{userPassword})")
	@Options(useGeneratedKeys=true,keyProperty="userId")
	public void Save(User user);

	
	@Select("select * from person")
	public List<UserInfo> selectAll();
}
