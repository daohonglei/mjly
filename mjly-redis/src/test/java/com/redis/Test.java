package com.redis;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.redis.dao.UserDao;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {
	@Autowired
	private UserDao userDaoImpl;

	@org.junit.Test
	public void test() {
		System.out.println(userDaoImpl.get(1L));
	}

}
