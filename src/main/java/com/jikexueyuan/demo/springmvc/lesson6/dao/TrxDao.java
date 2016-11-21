package com.jikexueyuan.demo.springmvc.lesson6.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.jikexueyuan.demo.springmvc.lesson6.entity.Trx;

public interface TrxDao {
	
	@Insert("insert into trx(contentid,personid,price,time) values(#{contentid},#{personid},#{price},#{time})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	public void saveTrx(Trx trx);
	
	@Select("select * from trx where personid=#{personid}")
	public List<Trx> Search(int personid);

	@Select("select * from trx where contentid=#{contentid} and personid=#{personid}")
	public Trx SearchProduct(Trx trx);
}
