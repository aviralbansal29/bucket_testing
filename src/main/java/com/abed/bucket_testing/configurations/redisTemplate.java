package com.abed.bucket_testing.configurations;

import org.springframework.context.annotation.Bean;

@Bean
JedisConnectionFactory jedisConnectionFactory() {
  return new JedisConnectionFactory();
}

@Bean
public RedisTemplate<String, Object> redisTemplate() {
  RedisTemplate<String, Object> template = new RedisTemplate<>();
  template.setConnectionFactory(jedisConnectionFactory());
  return template;
}
