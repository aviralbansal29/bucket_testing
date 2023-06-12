package com.abed.bucket_testing.common_services;

import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

  private final RedisTemplate<String, String> redisTemplate;
  private final ZSetOperations<String, String> zSetOperations;

  public RedisService(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.zSetOperations = redisTemplate.opsForZSet();
  }

  public void addToOrderedSet(String key, String value, double score) {
    zSetOperations.add(key, value, score);
  }

  public Set<String> getRangeFromOrderedSet(String key, long start, long end) {
    return zSetOperations.range(key, start, end);
  }

  public Double getScore(String key, String value) {
    return zSetOperations.score(key, value);
  }
}
