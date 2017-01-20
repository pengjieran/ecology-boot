package com.mtoliv.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class RedisCacheConfig {

	@Autowired
	private Environment environment;
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig poolConfig) {
		
		JedisConnectionFactory factory = new JedisConnectionFactory(poolConfig);
		factory.setDatabase(environment.getProperty("spring.redis.database", Integer.class));
		factory.setHostName(environment.getProperty("spring.redis.host"));
		factory.setPassword(environment.getProperty("spring.redis.password"));
		factory.setPort(environment.getProperty("spring.redis.port", Integer.class));
		return factory;
	}
	
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMinIdle(environment.getProperty("spring.redis.pool.min-idle", Integer.class));
		config.setMaxIdle(environment.getProperty("spring.redis.pool.max-idle", Integer.class));
		return config;
	}
}