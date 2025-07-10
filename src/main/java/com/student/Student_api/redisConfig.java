package com.student.Student_api;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@EnableCaching
public class redisConfig {
	
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		  return RedisCacheManager.create(connectionFactory);
	}
	
	  @Bean
	    public LettuceConnectionFactory redisConnectionFactory() {
	        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
	        config.setHostName("redis-12484.c89.us-east-1-3.ec2.redns.redis-cloud.com");
	        config.setPort(12484);
	        config.setUsername("default");
	        config.setPassword("5h462U8UeiGXuvtANHL7OkLdszARi4bY");
	        return new LettuceConnectionFactory(config);
	    }

}