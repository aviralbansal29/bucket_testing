package com.abed.bucket_testing.experiments.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidExperimentIDValidator.class)
public @interface ValidExperimentID {
  String message() default "Experiment not found";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
