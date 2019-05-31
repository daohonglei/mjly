package com.redis.dao;

import com.redis.bean.User;

public interface UserDao {
	public void save(User user,Long expireTime);
	public User get(Long id);
}
