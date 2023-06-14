package com.abed.bucket_testing.common_services;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

  @Autowired private StringRedisTemplate redisTemplate;

  public void addData(String key, long val, long score) {
    redisTemplate.opsForZSet().add(key, String.valueOf(val), score);
  }

  public long getScore(String key, long val) {
    Double score = redisTemplate.opsForZSet().score(key, String.valueOf(val));
    if (score == null) {
      return 0L;
    }
    return (long)score.doubleValue();
  }
}
