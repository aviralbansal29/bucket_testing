package com.abed.bucket_testing.variants;

import com.abed.bucket_testing.annotations.FieldNotBlank;
import com.abed.bucket_testing.exceptions.InvalidRequestException;
import jakarta.validation.constraints.NotNull;
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
  @NotNull
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

  public FieldError setControlVariant() {
    List<VariantModel> controlVariants = variantRepository.findByExperimentIdAndName(experiment_id, "control");
    System.out.println(controlVariants.size());
    if (controlVariants.size() != 1) {
      return new FieldError("ExperimentModel", "experiment_id",
          "Control Variant not found");
    }
    this.controlVariant = controlVariants.get(0);
    return null;
  }

  public void Validate(long id) throws Exception {
    this.id = id;
    validateId();
    InvalidRequestException exception = new InvalidRequestException();
    FieldError err = setControlVariant();
    if (err != null) {
      exception.addError(err);
      throw exception;
    }
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
    byte controlVariantWeigtage = controlVariant.getWeightage();
    if (weightage > controlVariantWeigtage) {
      return new FieldError("VariantModel", "weightage",
          "Weightage cannot be more than " +
              controlVariantWeigtage);
    }
    return null;
  }

  private FieldError validateControlVariantImmutability() {
    if (controlVariant.getId() == currentVariant.getId()) {
      System.out.println(name);
      if (!name.equals("control")) {
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
