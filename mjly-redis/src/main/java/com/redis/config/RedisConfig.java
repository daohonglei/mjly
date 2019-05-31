package com.redis.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	
	@Bean
	public StringRedisTemplate getStringRedisTemplate(){
		Set<RedisNode> sentinels=new HashSet<>();
		sentinels.add(new RedisNode("127.0.0.1", 6379));
		
		RedisSentinelConfiguration sentinelConfig=new RedisSentinelConfiguration();
		
		sentinelConfig.setSentinels(sentinels);
		
		JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
		
		RedisConnectionFactory connectionFactory=new JedisConnectionFactory(sentinelConfig,jedisPoolConfig);
		
		StringRedisTemplate stringRedisTemplate=new StringRedisTemplate(connectionFactory);
		return stringRedisTemplate;
	}
}
