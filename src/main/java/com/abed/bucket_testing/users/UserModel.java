package com.abed.bucket_testing.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * UserModel defines table schema for users table
 */
@Entity
@Table(name = "users")
public class UserModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "email")
  private String email;

  public UserModel() {
  }

  public UserModel(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
