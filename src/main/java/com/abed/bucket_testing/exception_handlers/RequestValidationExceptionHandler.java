package com.abed.bucket_testing.exception_handlers;

import com.abed.bucket_testing.exceptions.InvalidRequestException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestValidationExceptionHandler {

  @ExceptionHandler(InvalidRequestException.class)
  @ResponseStatus
  public Map<String, Map<String, String>>
  handleInvalidRequestException(InvalidRequestException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getErrors().forEach((error) -> {
      String fieldName = error.getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    Map<String, Map<String, String>> resp = new HashMap<>();
    resp.put("errors", errors);
    return resp;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus
  public Map<String, Map<String, String>>
  handleMethodArgumentNotValidExcepion(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError)error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    Map<String, Map<String, String>> resp = new HashMap<>();
    resp.put("errors", errors);
    return resp;
  }
}
