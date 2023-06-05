package com.abed.bucket_testing.experiments;

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
 * Manages repository for ExperimentModel
 */
public interface ExperimentRepository
    extends JpaRepository<ExperimentModel, Long>, ExperimentRepositoryCustom {

  @Query(
      "SELECT exists(SELECT 1 from ExperimentModel where lower(name) = lower(:name))")
  boolean
  existsByCaseInsensitiveName(String name);
}

interface ExperimentRepositoryCustom {
  List<ExperimentModel> findByCriteria(String name, Boolean isPublished);
}

/**
 * Custom Implementation for Experiment Repository Custom interface
 */
class ExperimentRepositoryCustomImpl implements ExperimentRepositoryCustom {

  @PersistenceContext private EntityManager entityManager;

  @Override
  public List<ExperimentModel> findByCriteria(String nameSubString,
                                              Boolean isPublished) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<ExperimentModel> query =
        cb.createQuery(ExperimentModel.class);
    Root<ExperimentModel> experiment = query.from(ExperimentModel.class);

    List<Predicate> predicates = new ArrayList<>();

    if (nameSubString != null && !nameSubString.isEmpty()) {
      predicates.add(cb.like(experiment.get("name"), nameSubString));
    }

    if (isPublished != null) {
      predicates.add(cb.equal(experiment.get("isPublished"), isPublished));
    }

    query.select(experiment)
        .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
    return entityManager.createQuery(query).getResultList();
  }
}
