package com.abed.bucket_testing.variants;

import com.abed.bucket_testing.annotations.FieldNotBlank;
import com.abed.bucket_testing.exceptions.InvalidRequestException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.validation.FieldError;

/**
 * Defines structure for update request
 */
public class VariantUpdateRequest {

  @FieldNotBlank
  private String name;
  @FieldNotBlank
  private String description;
  @FieldNotBlank
  private Byte weightage;

  private long id;
  private long experiment_id;

  private VariantModel controlVariant;
  private VariantModel currentVariant;
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

  public Byte getWeightage() {
    return weightage;
  }

  public VariantModel getControlVariant() {
    return controlVariant;
  }

  public VariantModel getCurrentVariant() {
    return currentVariant;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setWeightage(Byte weightage) {
    this.weightage = weightage;
  }

  public void setExperiment_id(long experiment_id) {
    this.experiment_id = experiment_id;
  }

  public void setVariantRepository(VariantRepository variantRepository) {
    this.variantRepository = variantRepository;
  }

  public void Validate(long id) throws Exception {
    this.id = id;
    validateId();
    InvalidRequestException exception = new InvalidRequestException();
    exception.addError(validateUniqueName());
    exception.addError(validateWeightage());
    exception.addError(validateControlVariantImmutability());

    if (exception.getErrors().size() > 0) {
      throw exception;
    }
  }

  private void validateId() throws Exception {
    Optional<VariantModel> variant = variantRepository.findById(id);
    if (variant.isEmpty()) {
      throw new NotFoundException();
    }
    currentVariant = variant.get();
  }

  private FieldError validateUniqueName() {
    if (variantRepository.existsByExperimentIdAndNameAndNotId(experiment_id,
        name, this.id)) {
      return new FieldError("VariantModel", "name", "Duplicate Entry");
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

  private FieldError validateControlVariantImmutability() {
    if (controlVariant.getId() == currentVariant.getId()) {
      if (name != "control") {
        return new FieldError("VariantModel", "name",
            "Immutable for control variant.");
      }
      if (weightage != currentVariant.getWeightage()) {
        return new FieldError("VariantModel", "weightage",
            "Immutable for control variant.");
      }
    }
    return null;
  }
}
