package com.jikexueyuan.demo.springmvc.lesson6.service.impl;

import java.io.IOException;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class AopTest {
	public static void main(String[] args) throws IOException {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext1.xml");
		AopServiceA aopServiceA=context.getBean(AopServiceA.class);
		AopServiceB aopServiceB=context.getBean(AopServiceB.class);
		aopServiceA.insertId(111);
		aopServiceB.insertId("lilei");
		context.close();
	}
}
