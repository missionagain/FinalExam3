package com.jikexueyuan.demo.springmvc.lesson6.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jikexueyuan.demo.springmvc.lesson6.dao.PersonDao;
import com.jikexueyuan.demo.springmvc.lesson6.dao.ProductDao;
import com.jikexueyuan.demo.springmvc.lesson6.dao.TrxDao;
import com.jikexueyuan.demo.springmvc.lesson6.entity.Person;
import com.jikexueyuan.demo.springmvc.lesson6.entity.Product;
import com.jikexueyuan.demo.springmvc.lesson6.entity.Trx;


@Controller
@SessionAttributes("loginUser")
public class SellerController {

@Autowired
PersonDao personDao;
@Resource
ProductDao pdtDao;

@Autowired
TrxDao trxDao;

@Autowired
private HttpServletRequest request;


@RequestMapping(value="/public", method = RequestMethod.GET)	
public String publicProduct(@ModelAttribute("loginUser") Person loginuser,Model model){
	model.addAttribute("user", loginuser);
	return "public";
}

@RequestMapping(value="/edit",method = RequestMethod.POST)	
public String editProduct(@ModelAttribute("loginUser") Person loginuser,Model model,@RequestParam Integer id){
	Product product=pdtDao.search(id);
	model.addAttribute("user", loginuser);
	model.addAttribute("product", product);
	return "edit";
}

@RequestMapping(value="/publicSubmit", method = RequestMethod.POST)	
public String publicSubmit(@ModelAttribute("loginUser") Person loginuser,@ModelAttribute Product pdt,Model model){
	pdt.setPrice(pdt.getPrice()*100);
	pdtDao.Save(pdt);
	pdt.setId(pdtDao.maxid());
	model.addAttribute("user", loginuser);
	model.addAttribute("product", pdt);
	return "publicSubmit";
}

@RequestMapping(value="/editSubmit", method = RequestMethod.POST)	
public String editSubmit(@ModelAttribute("loginUser") Person loginuser,@ModelAttribute Product pdt,Model model,@RequestParam Integer id){
	pdt.setId(id);
	pdt.setPrice(pdt.getPrice()*100);
	pdtDao.update(pdt);
	model.addAttribute("user", loginuser);
	model.addAttribute("product", pdt);
	System.out.println(pdt.getTitle());
	return "editSubmit";
}

@RequestMapping(value="/api/delete")
public void apiDelete(@ModelAttribute("loginUser") Person user,Model model,@RequestParam Integer id){
	pdtDao.removeProduct(id);
	model.addAttribute("user", user); 	
	model.addAttribute("code", 200);
	return;
	}

}
