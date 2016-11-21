package com.jikexueyuan.demo.springmvc.lesson6.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jikexueyuan.demo.springmvc.lesson6.entity.Person;

public interface PersonDao {
	@Select("select * from person where userName=#{userName} and password=#{password}")
	public Person login(@Param("userName")String userName,@Param("password")String password);
	
	@Select("select * from person where id=#{id}")
	public Person search(@Param("id") Integer id);
}
