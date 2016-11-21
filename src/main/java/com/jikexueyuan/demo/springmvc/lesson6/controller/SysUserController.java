package com.jikexueyuan.demo.springmvc.lesson6.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jikexueyuan.demo.springmvc.lesson6.dao.LoginDao;
import com.jikexueyuan.demo.springmvc.lesson6.entity.Blog;
import com.jikexueyuan.demo.springmvc.lesson6.entity.SysUser;
import com.jikexueyuan.demo.springmvc.lesson6.entity.User;
import com.jikexueyuan.demo.springmvc.lesson6.entity.UserInfo;
import com.jikexueyuan.demo.springmvc.lesson6.service.BlogService;
import com.jikexueyuan.demo.springmvc.lesson6.service.ISysUserService;

@Controller
public class SysUserController {
	
	@Resource
	ISysUserService service;
	
	@Resource
	BlogService blogService;
	
	@Resource
	LoginDao loginDao;
	
	@Autowired
	private HttpServletRequest request;
	
	
	public Boolean loginCheck(){
		
		Boolean alreadyLogin=false;
		
		Cookie[] cookies = request.getCookies();
		  String uname = "";  
		  String upassword = ""; 
		 if(cookies!=null)  
		 {  
			  for (int i = 0; i < cookies.length; i++) 
			  { 
			    Cookie c = cookies[i];       
				    if(c.getName().equalsIgnoreCase("userName"))  
				    {  
				       uname = c.getValue();  
				    }  
				    else if(c.getName().equalsIgnoreCase("userPassword"))  
				    {  
				       upassword = c.getValue();  
				    }

			  }
		 }
		 
	   if(loginDao.login(uname, upassword)!=null){
		   alreadyLogin=true;
			
	   }
	   else
	   		alreadyLogin=false;		
	   		
	   		return alreadyLogin;
	}
	
	
//--------------------------------------------------------------	
	@RequestMapping(value="/json")
	public Map jsonInfo(Map map){
	List<UserInfo> allUser=loginDao.selectAll();
	map.put("allUser", allUser);
	return map;
}
	
	
	@RequestMapping(value="/html")
	public String htmlInfo(Model model){
	List<UserInfo> allUser=loginDao.selectAll();
	model.addAttribute("allUser", allUser);
	
	return "htmlInfo";
}
	
	
	//作业学习页面---------------------------------------------------------
	@RequestMapping("/blog")
	public @ResponseBody String blogInfo(@RequestParam("blogTitle") String blogTitle, @RequestParam("blogContent") String blogContent)
			throws IOException {
	Blog blog= new Blog();
	blog.setBlogTitle(blogTitle);
	blog.setBlogContent(blogContent);
	
	if(blogTitle.length()>20||blogContent.length()>100){
	    return "400";}
	else
		blogService.saveBlog(blog);
		return "200";
	}
	
	
	
	@RequestMapping(value="/login2")
	public String loginInfo(@ModelAttribute User user,Model model,HttpServletRequest request,HttpServletResponse response)
			throws IOException {
		if(loginDao.loginUser(user)!=null){
			User userInfo=loginDao.loginUser(user);
			userInfo.setUserType(1);
			model.addAttribute(userInfo);
			return "index";
			}
		
		else
			return "redirect:/404.jsp";

	}
	
		
}
