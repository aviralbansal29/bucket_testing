package com.abed.bucket_testing.variants;

import com.abed.bucket_testing.annotations.FieldNotBlank;
import com.abed.bucket_testing.experiments.validations.ValidExperimentID;
import jakarta.validation.constraints.NotNull;

/**
 * Defines structure for variant create request
 */
public class VariantCreateRequest {

  @FieldNotBlank private String name;
  @FieldNotBlank private String description;
  @NotNull @ValidExperimentID private long experiment_id;

  public String getName() { return name; }

  public String getDescription() { return description; }

  public long getExperiment_id() { return experiment_id; }

  public void setName(String name) { this.name = name; }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setExperiment_id(long experiment_id) {
    this.experiment_id = experiment_id;
  }
}
