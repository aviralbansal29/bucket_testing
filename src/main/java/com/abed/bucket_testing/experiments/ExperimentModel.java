package com.abed.bucket_testing.experiments;

import com.abed.bucket_testing.variants.VariantModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;

/**
 * Manages entity for schema of experiments table
 */
@Entity
@Table(name = "experiments")
public class ExperimentModel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "is_published", nullable = true)
  private Boolean isPublished = false;

  @OneToMany
  @JoinColumn(name = "experiment_id")
  @JsonIgnore
  private Set<VariantModel> variants;

  public ExperimentModel() {
  }

  public ExperimentModel(String name, String description) {
    this.name = name;
    this.description = description;
  }

  @Override
  public String toString() {
    return "Experiment => id: " + id + ", name: " + name +
        ", description: " + description;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Boolean getIsPublished() {
    return isPublished;
  }

  public Set<VariantModel> getVariants() {
    return variants;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setIsPublished(Boolean isPublished) {
    isPublished = isPublished;
  }

  public void setVariants(Set<VariantModel> variants) {
    this.variants = variants;
  }
}
