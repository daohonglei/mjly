package com.redis.dao.impl;

import org.springframework.stereotype.Component;

import com.redis.bean.User;
import com.redis.dao.BaseDao;
import com.redis.dao.UserDao;

@Component
public class UserDaoImpl extends BaseDao implements UserDao {

	@Override
	public void save(User user,Long expireTime) {
		super.save(user.getId(),user,expireTime);
	}

	@Override
	public User get(Long id) {
		return (User)super.get(id);
	}
}
