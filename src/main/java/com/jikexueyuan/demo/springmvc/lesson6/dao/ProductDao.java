package com.jikexueyuan.demo.springmvc.lesson6.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jikexueyuan.demo.springmvc.lesson6.entity.Blog;
import com.jikexueyuan.demo.springmvc.lesson6.entity.Product;

public interface ProductDao {
	@Insert("insert into product(title,summary,detail,price,image) values(#{title}, #{summary},#{detail}, #{price}, #{image})")
	@Options(useGeneratedKeys=true,keyProperty="uid")
	public void Save(Product pdt);
	
	@Select("select * from product where id=#{id}")
	public Product search(int id);
	
	@Select("select * from product")
	public List<Product> searchAll();
	
	@Update("update product set isBuy=1,isSell=1 where id=#{id}")
	public void buyProduct(Integer id);

	@Select("select max(id) from product")
	public int maxid();
	
	@Update("update product set title=#{title},summary=#{summary},detail=#{detail},image=#{image},price=#{price} where id=#{id}")
	public void update(Product pdt);
	
	@Delete("delete from product where id=#{id}")
	public void removeProduct(Integer id);
}
