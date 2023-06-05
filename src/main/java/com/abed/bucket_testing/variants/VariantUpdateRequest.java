package com.abed.bucket_testing.variants;

import com.abed.bucket_testing.annotations.FieldNotBlank;

/**
 * Defines structure for update request
 */
public class VariantUpdateRequest {

  @FieldNotBlank private String name;
  @FieldNotBlank private String description;

  public String getName() { return name; }

  public String getDescription() { return description; }

  public void setName(String name) { this.name = name; }

  public void setDescription(String description) {
    this.description = description;
  }
}
