package com.abed.bucket_testing.variants;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for variants table
 */
public interface VariantRepository extends JpaRepository<VariantModel, Long> {
  @Query(
      "Select v from VariantModel v where v.experiment_id = :experimentId AND LOWER(v.name) = LOWER(:name)")
  List<VariantModel>
  findByExperimentIdAndName(Long experimentId, String name);

  @Query(
      "SELECT v from VariantModel v WHERE v.id <> :id AND v.experiment_id = :experimentId AND LOWER(v.name) = LOWER(:name)")
  List<VariantModel>
  findByExperimentIdAndNameAndNotId(Long experimentId, String name, Long id);
}
