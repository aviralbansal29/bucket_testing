package com.abed.bucket_testing.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {})
@NotBlank(message = "This field cannot be blank",
          groups = FieldNotBlank.ValidationGroup.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldNotBlank {
  String message() default "FieldNotBlank Violation";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  interface ValidationGroup {}
}
