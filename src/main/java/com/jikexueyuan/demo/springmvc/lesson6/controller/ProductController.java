package com.jikexueyuan.demo.springmvc.lesson6.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jikexueyuan.demo.springmvc.lesson6.dao.ProductDao;
import com.jikexueyuan.demo.springmvc.lesson6.entity.Product;
import com.jikexueyuan.demo.springmvc.lesson6.entity.User;

@Controller
public class ProductController {
	
	@Resource
	ProductDao pdtDao;
	
	@RequestMapping(value="/editsave")
	public String saveProduct(@ModelAttribute Product pdt,Model model){
		pdtDao.Save(pdt);
		model.addAttribute("pdt", pdt);
		List<Product> pdtList=new ArrayList<Product>();
		pdtList=pdtDao.searchAll();
		model.addAttribute("pdtList", pdtList);
		User user=new User();
		user.setUserName("LEO");
		user.setUserType(0);
		model.addAttribute("user", user);
		return "index";
		
	}
	
	@RequestMapping(value="/show/{id}")
	public String showProduct(@PathVariable("id")int id,Model model){
		Product pdt2=pdtDao.search(id);
		model.addAttribute("product",pdt2);
		return "show";
	}
	
	@RequestMapping(value="/account")
	public String showAccount(){
		 return"redirect:/account.html";
	}
	
}
