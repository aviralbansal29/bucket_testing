package com.abed.bucket_testing.exceptions;

import java.util.ArrayList;
import org.springframework.validation.FieldError;

/**
 * Exception to manually raise field errors
 */
public class InvalidRequestException extends Exception {

  private ArrayList<FieldError> errors;

  public InvalidRequestException() { this.errors = new ArrayList<>(); }

  public void addError(FieldError err) {
    if (err != null) {
      errors.add(err);
    }
  }

  public ArrayList<FieldError> getErrors() { return errors; }
}
