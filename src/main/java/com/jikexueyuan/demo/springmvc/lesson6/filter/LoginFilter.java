package com.jikexueyuan.demo.springmvc.lesson6.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.jikexueyuan.demo.springmvc.lesson6.dao.LoginDao;

public class LoginFilter {

	@Resource
	LoginDao loginDao;
	
	@Autowired
	private HttpServletRequest request;
	
	 public void doFilter(ServletRequest request, ServletResponse response,
			 
		      FilterChain chain) throws IOException, ServletException {
		 
		
		 
		    if(!loginCheck()){
		 
		      System.out.println("你还没有登录");
		 
		      HttpServletResponse resp = (HttpServletResponse) response;
		 
		      resp.sendRedirect("/login2.html");
		 
		    }else{
		 	      chain.doFilter(request, response);
		 
		    }
		 
		  }
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
}
