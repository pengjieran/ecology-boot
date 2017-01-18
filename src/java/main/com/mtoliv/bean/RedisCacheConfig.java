package com.mtoliv.bean;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisCacheConfig {
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig poolConfig) {
		
		JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
		factory.setDatabase(2);
		factory.setHostName("10.10.60.116");
		factory.setPassword("123456");
		factory.setPort(6379);
		return factory;
	}
	
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMinIdle(10);
		config.setMaxTotal(100);
		return config;
	}

}