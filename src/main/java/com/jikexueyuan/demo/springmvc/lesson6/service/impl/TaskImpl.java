package com.jikexueyuan.demo.springmvc.lesson6.service.impl;

import com.jikexueyuan.demo.springmvc.lesson6.service.Task01;

public class TaskImpl implements Task01 {

	public String getUsers(String a) {
		return"redirect:/login2.html";

	}
	public static void main(String[] args) {
		TaskImpl task=new TaskImpl();
		task.getUsers("a");
	}
}
