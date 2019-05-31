package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redis.bean.User;
import com.redis.dao.UserDao;

@RestController
public class ServiceController {
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/getUser")
	public User getUser(Long id) {
		User user=userDao.get(id);
		System.out.println(user);
		return user;
	}
}
