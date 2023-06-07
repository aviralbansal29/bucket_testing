package com.abed.bucket_testing.experiments;

import com.abed.bucket_testing.annotations.FieldNotBlank;
import com.abed.bucket_testing.exceptions.InvalidRequestException;
import org.springframework.validation.FieldError;

/**
 * Manages request data for handling create request
 */
public class ExperimentCreateRequest {

  @FieldNotBlank
  private String name;
  @FieldNotBlank
  private String description;

  private ExperimentRepository experimentRepository;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setExperimentRepository(ExperimentRepository experimentRepository) {
    this.experimentRepository = experimentRepository;
  }

  public void Validate() throws InvalidRequestException {
    InvalidRequestException exception = new InvalidRequestException();
    exception.addError(validateUniqueName());

    if (exception.getErrors().size() > 0) {
      throw exception;
    }
  }

  FieldError validateUniqueName() {
    if (experimentRepository.existsByCaseInsensitiveName(getName())) {
      return new FieldError("ExperimentModel", "name", "Duplicate entry.");
    }
    return null;
  }
}
