package com.abed.bucket_testing.dto;

import java.util.List;

/**
 * ListServiceResponse
 */
public class ListServiceResponse<T> {

  private long count;
  private List<T> content;

  public ListServiceResponse(long count, List<T> content) {
    this.count = count;
    this.content = content;
  }

  public long getCount() {
    return count;
  }

  public List<T> getContent() {
    return content;
  }
}
