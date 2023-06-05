package com.abed.bucket_testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class BucketTestingServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(BucketTestingServiceApplication.class, args);
  }
}
