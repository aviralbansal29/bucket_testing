package com.abed.bucket_testing.experiments;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

/**
 * ExperimentService
 */
public class ExperimentService {

  @Autowired ExperimentRepository experimentRepository;

  public ExperimentModel createExperiment(ExperimentCreateRequest req)
      throws Exception {
    req.Validate();
    ExperimentModel experiment =
        new ExperimentModel(req.getName(), req.getDescription());
    experimentRepository.save(experiment);
    return experiment;
  }

  public List<ExperimentModel> listExperiments(String nameSubString,
                                               Boolean isPublished) {
    List<ExperimentModel> experiments =
        experimentRepository.findByCriteria(nameSubString, isPublished);
    return experiments;
  }

  public ExperimentModel retrieveExperiment(long id) throws Exception {
    Optional<ExperimentModel> experiment = experimentRepository.findById(id);
    if (experiment.isEmpty()) {
      throw new NotFoundException();
    }
    return experiment.get();
  }
}
