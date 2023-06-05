package com.abed.bucket_testing.experiments.validations;

import com.abed.bucket_testing.experiments.ExperimentModel;
import com.abed.bucket_testing.experiments.ExperimentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidExperimentIDValidator
    implements ConstraintValidator<ValidExperimentID, Long> {

  @Autowired ExperimentRepository expRepo;

  @Override
  public void initialize(ValidExperimentID expID) {}

  @Override
  public boolean isValid(Long expID, ConstraintValidatorContext cxt) {
    Optional<ExperimentModel> exp = expRepo.findById(expID);
    return exp.isPresent();
  }
}
