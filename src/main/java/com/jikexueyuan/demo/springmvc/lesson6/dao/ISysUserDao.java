package com.jikexueyuan.demo.springmvc.lesson6.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jikexueyuan.demo.springmvc.lesson6.entity.SysUser;

@Repository
public interface ISysUserDao {
	    public void save(SysUser user);
	    public SysUser selectById(int uId);
	    public List<SysUser> selectAll();
	    public void deleteById(int uId);
}
