package com.abed.bucket_testing.dto;

import java.util.List;

/**
 * ListServiceResponse
 */
public class ListServiceResponse {

  private long count;
  private List<?> content;

  public ListServiceResponse(long count, List<?> content) {
    this.count = count;
    this.content = content;
  }

  public long getCount() {
    return count;
  }

  public List<?> getContent() {
    return content;
  }
}
