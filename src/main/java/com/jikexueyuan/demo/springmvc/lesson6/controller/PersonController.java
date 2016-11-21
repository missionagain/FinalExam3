package com.jikexueyuan.demo.springmvc.lesson6.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.jikexueyuan.demo.springmvc.lesson6.dao.PersonDao;
import com.jikexueyuan.demo.springmvc.lesson6.dao.ProductDao;
import com.jikexueyuan.demo.springmvc.lesson6.dao.TrxDao;
import com.jikexueyuan.demo.springmvc.lesson6.entity.Person;
import com.jikexueyuan.demo.springmvc.lesson6.entity.Product;
import com.jikexueyuan.demo.springmvc.lesson6.entity.ProductForAccount;
import com.jikexueyuan.demo.springmvc.lesson6.entity.Trx;

@Controller
@SessionAttributes("loginUser")
public class PersonController {

@Autowired
PersonDao personDao;
@Resource
ProductDao pdtDao;

@Autowired
TrxDao trxDao;

@Autowired
private HttpServletRequest request;



@RequestMapping(value={"/homeLogin"})
public String homeLogin(@ModelAttribute Person homeLoginPerson,Model model,@RequestParam(value="type",required=false) Integer type,HttpServletResponse response){
	
	Person person=personDao.login(homeLoginPerson.getUserName(),homeLoginPerson.getPassword());
	
   //person不为空的话，说明登陆成功，将此用户信息保存用以后续页面匹配验证;不成功则不返回，说明没有成功登陆的用户。
		if(person!=null){
			model.addAttribute("user", person);
			model.addAttribute("loginUser", person);
			//Cookie userNameCookie=new Cookie("userName",homeLoginPerson.getUserName());
			//Cookie passwordCookie=new Cookie("password",homeLoginPerson.getPassword());
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("userName", homeLoginPerson.getUserName());
			httpSession.setAttribute("password", homeLoginPerson.getPassword());
			//System.out.println(httpSession.getAttribute("userName"));
			//userNameCookie.setMaxAge(7*24*3600);
			//passwordCookie.setMaxAge(7*24*3600);
			//response.addCookie(userNameCookie);
			//response.addCookie(passwordCookie);
		}
	
	List<Product> pdtList=new ArrayList<Product>();
	pdtList=pdtDao.searchAll();
	model.addAttribute("productList", pdtList);
	
	request.setAttribute("type", 1);
	return "index";
	}


@RequestMapping(value={"/home","/"})
public String login(Model model,@RequestParam(value="type",required=false) Integer type){
	
	//Person user=personDao.login(person.getUserName(), person.getPassword());
	Person personFromCookies=isFirstLogin();//可能该user没有任何信息，但ftl会查询不到属性信息报错,所以下面到DAO中check一下
	Person person=personDao.login(personFromCookies.getUserName(),personFromCookies.getPassword());
	
   //person不为空的话，说明登陆成功，将此用户信息保存用以后续页面匹配验证;不成功则不返回，说明没有成功登陆的用户。
		if(person!=null){
			model.addAttribute("user", person);
			model.addAttribute("loginUser", person);
				if(type!=null)
				request.setAttribute("type", type);
				else{
					request.setAttribute("type", person.getUserType());	
				}
		}
		else{
			request.setAttribute("type", 1);
		}
	List<Product> pdtList=new ArrayList<Product>();
	pdtList=pdtDao.searchAll();
	model.addAttribute("productList", pdtList);
	
	
	return "index";
	}


//login page
@RequestMapping(value="/login", method = RequestMethod.GET)	
public String userLogin(){
	return "login";
}

@RequestMapping(value="/logout", method = RequestMethod.GET)	
public String userLoginOut(SessionStatus sessionstatus,HttpServletResponse response){
	//Cookie userName=new Cookie("userName","");
	//response.addCookie(userName);	
	HttpSession session=request.getSession();
	if(session!=null){
		sessionstatus.setComplete();
		session.invalidate();			
	}
	request.setAttribute("type", 0);
	return "login";
}


@RequestMapping(value="/show")	
public String showProduct(Model model,@RequestParam Integer id){
	Product product=pdtDao.search(id);
	product.setPrice(product.getPrice());
	model.addAttribute("product", product);
	
	
	if(model.containsAttribute("loginUser")){
		Person loginuser=(Person) model.asMap().get("loginUser");
		model.addAttribute("user", loginuser);
		if(product.getIsBuy()){
			Trx trx=new Trx();
			trx.setContentid(id);
			trx.setPersonid(loginuser.getPid());
			Trx trx2=trxDao.SearchProduct(trx);
			model.addAttribute("trxProduct", trx2);
		}
	}
	return "show";
}


//三期改为直接直接购买跳转至账务页面
@RequestMapping(value="/api/buy")
public void apibuy(@ModelAttribute("loginUser") Person user,Model model,@RequestParam Integer id){
	//create trx information;
	Trx trx=new Trx();
	trx.setContentid(id);
	trx.setPersonid(user.getPid());
	trx.setPrice(pdtDao.search(id).getPrice());
			Date date=new Date();
	trx.setTime(date.getTime());  
	trxDao.saveTrx(trx);
	//change the product condition;
	pdtDao.buyProduct(id);
	model.addAttribute("user", user); 	
	model.addAttribute("code", 200);
	return;
	
}



//shoping cart
@RequestMapping(value="/cart")
public String buyerSettleAccount(@ModelAttribute("loginUser") Person user,Model model){
	List<ProductForAccount> buyList=new ArrayList<ProductForAccount>();
	List<Trx> trxList=trxDao.Search(user.getPid());

	for(Trx trx:trxList){
		ProductForAccount pdt=new ProductForAccount();
		pdt.setId(trx.getContentid());
		pdt.setTitle(pdtDao.search(trx.getContentid()).getTitle());
		pdt.setBuyPrice(trx.getPrice());
		pdt.setBuyTime(trx.getTime());
		pdt.setImage(pdtDao.search(trx.getContentid()).getImage());
		buyList.add(pdt);
	}
	model.addAttribute("buyList", buyList);
	model.addAttribute("user", user); 
	return "account";
}



@RequestMapping(value="/edit")	
public String editProduct(@ModelAttribute("loginUser") Person person,Model model,@RequestParam Integer id){
	Product product=pdtDao.search(id);
	model.addAttribute("product", product);
	return "edit";
}

/*
public Person isFirstLogin(){
	  Person cookiePerson=new Person();
	  String uname = "";  
	  String upassword = "";
	  
	  Cookie[] cookies = request.getCookies();  
		 if(cookies!=null)  
		 {  
			  for (int i = 0; i < cookies.length; i++) 
			  { 
			    Cookie c = cookies[i];       
				    if(c.getName().equalsIgnoreCase("userName"))  
				    {  
				       uname = c.getValue();  
				    }  
				    else if(c.getName().equalsIgnoreCase("password"))  
				    {  
				       upassword = c.getValue();  
				    }
			  	}
		 }
		 if(personDao.login(uname, upassword)!=null){
			cookiePerson=personDao.login(uname, upassword);
		}
		 
	        return cookiePerson;
	}
}
*/
public Person isFirstLogin(){
	  Person cookiePerson=new Person();
	  String uname = "";  
	  String upassword = "";
	  
	  HttpSession httpSession = request.getSession(); 
		uname=(String) httpSession.getAttribute("userName");
		upassword=(String) httpSession.getAttribute("password");
		
		//System.out.println(uname+"   "+upassword);
		
		 if(personDao.login(uname, upassword)!=null){
			cookiePerson=personDao.login(uname, upassword);
		}
		 
	        return cookiePerson;
	}
}


/*
@RequestMapping(value="/login")
public ModelAndView login(@ModelAttribute Person person,Model model,@RequestParam(value="type",required=false) String type){
	ModelAndView md=new ModelAndView();		
	md.setViewName("index");
	
	Person user=personDao.login(person.getUserName(), person.getPassword());
	model.addAttribute("user", user);
	
	List<Product> pdtList=new ArrayList<Product>();
	pdtList=pdtDao.searchAll();
	model.addAttribute("pdtList", pdtList);
	
	request.setAttribute("type", user.getUserType());
	return md;
	}
}*/