package com.abed.bucket_testing.variants;

import com.abed.bucket_testing.experiments.ExperimentModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Model for defining schema of variants table
 */
@Entity
@Table(name = "variants")
public class VariantModel {

  @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;

  @Column(name = "name") private String name;

  @Column(name = "description") private String description;

  @Column(name = "weightage") private Byte weightage;

  @Column(name = "experiment_id") private long experiment_id;

  @ManyToOne
  @JoinColumn(name = "experiment_id", referencedColumnName = "id",
              nullable = false, insertable = false, updatable = false)
  @JsonIgnore
  private ExperimentModel experiment;

  public VariantModel() {}

  public VariantModel(String name, String description, Byte weightage,
                      long experiment_id) {
    this.name = name;
    this.description = description;
    this.weightage = weightage;
    this.experiment_id = experiment_id;
  }

  public long getId() { return id; }

  public String getName() { return name; }

  public String getDescription() { return description; }

  public Byte getWeightage() { return weightage; }

  public long getExperiment_id() { return experiment_id; }

  public ExperimentModel getExperiment() { return experiment; }

  public void setId(long id) { this.id = id; }

  public void setName(String name) { this.name = name; }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setWeightage(Byte weightage) { this.weightage = weightage; }

  public void setExperiment(ExperimentModel experiment) {
    this.experiment = experiment;
  }

  public void setExperiment_id(long experiment_id) {
    this.experiment_id = experiment_id;
  }
}
