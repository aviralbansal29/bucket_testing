package com.abed.bucket_testing.variants;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for variants table
 */
public interface VariantRepository
    extends JpaRepository<VariantModel, Long>, VariantRepositoryCustom {
  @Query("SELECT EXISTS(Select 1 from VariantModel v where v.experiment_id = :experimentId AND LOWER(v.name) = LOWER(:name))")
  boolean existsByExperimentIdAndName(Long experimentId, String name);

  @Query("SELECT EXISTS(SELECT 1 from VariantModel v WHERE v.id <> :id AND v.experiment_id = :experimentId AND LOWER(v.name) = LOWER(:name))")
  boolean existsByExperimentIdAndNameAndNotId(Long experimentId, String name, Long id);
}

interface VariantRepositoryCustom {
  List<VariantModel> findByCriteria(String name, Long experimentId);
}

/**
 * Custom Implementation for Variant Repository Custom interface
 */
class VariantRepositoryCustomImpl implements VariantRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<VariantModel> findByCriteria(String nameSubString,
      Long experimentId) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<VariantModel> query = cb.createQuery(VariantModel.class);
    Root<VariantModel> experiment = query.from(VariantModel.class);

    List<Predicate> predicates = new ArrayList<>();

    if (nameSubString != null && !nameSubString.isEmpty()) {
      predicates.add(cb.like(experiment.get("name"), nameSubString));
    }

    if (experimentId != null) {
      predicates.add(cb.equal(experiment.get("experiment_id"), experimentId));
    }

    query.select(experiment)
        .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
    return entityManager.createQuery(query).getResultList();
  }
}
