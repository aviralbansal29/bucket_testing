package com.abed.bucket_testing.users;

import com.abed.bucket_testing.annotations.FieldNotBlank;
import com.abed.bucket_testing.exceptions.InvalidRequestException;
import org.springframework.validation.FieldError;

/**
 * UserCreateRequest manages create request structure
 */
public class UserCreateRequest {

  @FieldNotBlank
  private String name;

  @FieldNotBlank
  private String email;

  private UserRepository userRepository;

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void Validate() throws InvalidRequestException {
    InvalidRequestException exception = new InvalidRequestException();
    exception.addError(validateUniqueEmail());

    if (exception.getErrors().size() > 0) {
      throw exception;
    }
  }

  private FieldError validateUniqueEmail() {
    if (userRepository.existsByEmail(email)) {
      return new FieldError("UserModel", "email", "Duplicate entry.");
    }
    return null;
  }
}
