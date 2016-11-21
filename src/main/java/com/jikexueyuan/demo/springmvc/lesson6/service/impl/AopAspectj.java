package com.jikexueyuan.demo.springmvc.lesson6.service.impl;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class AopAspectj {
//	@Pointcut("execution(* com.jikexueyuan.demo.springmvc.lesson6.service.impl.*.*(..))")
//	public void AopService(JoinPoint jp){
//	System.out.println(jp.toString());
//}
//	
//	@Before("AopService(JoinPoint jp)")
//	public void before(){
//		System.out.println("before method");
//	}
//	
//	@After("AopService(JoinPoint jp)")
//	public void after(){
//		System.out.println("before method");
//	}
	
	@Pointcut("execution(* com.jikexueyuan.demo.springmvc.lesson6.service.impl.*.*(..))")
	public void AopService(){
	System.out.println("haha");
}
	
	@Before("AopService()")
	public void before(){
		System.out.println("before method");
	}
	
	@After("AopService()")
	public void after(JoinPoint jp){
		System.out.println(jp.toShortString());
	}
	
//	@Around("AopService()")
//	public void around(ProceedingJoinPoint pjp) throws Throwable{
//		System.out.println("11111111111");
//		pjp.proceed();
//		System.out.println("22222222222222");
//	}
}
