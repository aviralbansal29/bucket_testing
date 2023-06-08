package com.abed.bucket_testing.variants;

import com.abed.bucket_testing.annotations.FieldNotBlank;
import com.abed.bucket_testing.exceptions.InvalidRequestException;
import com.abed.bucket_testing.experiments.validations.ValidExperimentID;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.validation.FieldError;

/**
 * Defines structure for variant create request
 */
public class VariantCreateRequest {

  @FieldNotBlank
  private String name;
  @FieldNotBlank
  private String description;
  @NotNull
  @ValidExperimentID
  private long experiment_id;
  @NotNull
  private byte weightage;

  private VariantModel controlVariant;

  private VariantRepository variantRepository;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public long getExperiment_id() {
    return experiment_id;
  }

  public byte getWeightage() {
    return weightage;
  }

  public VariantModel getControlVariant() {
    return controlVariant;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setExperiment_id(long experiment_id) {
    this.experiment_id = experiment_id;
  }

  public void setWeightage(byte weightage) {
    this.weightage = weightage;
  }

  public void setVariantRepository(VariantRepository variantRepository) {
    this.variantRepository = variantRepository;
  }

  public void Validate() throws InvalidRequestException {
    InvalidRequestException exception = new InvalidRequestException();
    exception.addError(validateUniqueName());
    exception.addError(validateWeightage());

    if (exception.getErrors().size() > 0) {
      throw exception;
    }
  }

  private FieldError validateUniqueName() {
    if (variantRepository.existsByExperimentIdAndName(experiment_id, name)) {
      return new FieldError("VariantModel", "name", "Duplicate entry.");
    }
    return null;
  }

  private FieldError validateWeightage() {
    List<VariantModel> controlVariant = variantRepository.findByCriteria("control", experiment_id);
    if (controlVariant.size() != 0) {
      return new FieldError("ExperimentModel", "experiment_id",
          "Control Variant not found");
    }
    byte controlVariantWeigtage = controlVariant.get(0).getWeightage();
    if (weightage > controlVariantWeigtage) {
      return new FieldError("VariantModel", "weightage",
          "Weightage cannot be more than " +
              controlVariantWeigtage);
    }
    return null;
  }
}
