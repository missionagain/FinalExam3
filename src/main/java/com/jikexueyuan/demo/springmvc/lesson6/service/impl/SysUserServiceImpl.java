package com.jikexueyuan.demo.springmvc.lesson6.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jikexueyuan.demo.springmvc.lesson6.dao.ISysUserDao;
import com.jikexueyuan.demo.springmvc.lesson6.entity.SysUser;
import com.jikexueyuan.demo.springmvc.lesson6.service.ISysUserService;

@Service
public class SysUserServiceImpl implements ISysUserService {
     
	@Resource
	JdbcTemplate jdbcTemplate;
	
	@Resource
	ISysUserDao dao;

	@Transactional
	public void saveWithJDBC(String uName, int uAge) {
		final String sql = "insert into sys_user(uName, uAge) values(?,?)";
		 jdbcTemplate.update(sql, new Object[]{uName, uAge});
		
	}

	public SysUser selectByIdWithJDBC(int uId) {
		String sql = "select * from sys_user where uId=?";
		final SysUser user = new SysUser();
		jdbcTemplate.query(sql, new Object[]{uId}, new RowCallbackHandler(){
			public void processRow(ResultSet rs) throws SQLException {
				user.setuName(rs.getString("uName"));
				user.setuAge(rs.getInt("uAge"));
			}
		});
		user.setuId(uId);
		return user;
	}

	public List<SysUser> selectAllWithJDBC() {
		String sql = "SELECT * from sys_user";
		final List<SysUser> userList = new ArrayList<SysUser>();
		jdbcTemplate.query(sql, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				SysUser user = new SysUser();
				user.setuName(rs.getString("uName"));
				user.setuAge(rs.getInt("uAge"));
				user.setuId(rs.getInt("uId"));
				userList.add(user);
			}
		});
		return userList;
	}

	@Transactional
	public void deleteByIdWithJDBC(int uId) {
		String sql = "DELETE from sys_user where uId=?";
		jdbcTemplate.update(sql, uId);
		
	}
	
    @Transactional
	public void saveWithMybatis(String uName, int uage) {
		SysUser user=new SysUser();
		user.setuName(uName);
		user.setuAge(uage);
		dao.save(user);
		
	}

	public SysUser selectByIdWithMybatis(int uId) {
		return dao.selectById(uId);
	}

	public List<SysUser> selectAllWithMybatis() {
		return dao.selectAll();
	}

	@Transactional
	public void deleteByIdWithMybatis(int uId) {
        dao.deleteById(uId);
		
	}
}
