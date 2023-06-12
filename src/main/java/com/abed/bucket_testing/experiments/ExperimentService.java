package com.abed.bucket_testing.experiments;

import com.abed.bucket_testing.dto.ListServiceResponse;
import com.abed.bucket_testing.variants.VariantService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

/**
 * Manages the business logic for Experiment Flow
 */
@Service
public class ExperimentService {

  @Autowired
  ExperimentRepository experimentRepository;

  @Autowired
  VariantService variantService;

  public ExperimentModel createExperiment(ExperimentCreateRequest req)
      throws Exception {
    req.Validate();
    ExperimentModel experiment = new ExperimentModel(req.getName(), req.getDescription());
    experimentRepository.save(experiment);
    variantService.createControlVariant(experiment.getId());
    return experiment;
  }

  public ListServiceResponse<ExperimentModel> listExperiments(Boolean isPublished, String query) {
    List<ExperimentModel> experiments = experimentRepository.findByCriteria(query, isPublished);
    return new ListServiceResponse<ExperimentModel>(
        experimentRepository.count(), experiments);
  }

  public ExperimentModel retrieveExperiment(long id) throws Exception {
    Optional<ExperimentModel> experiment = experimentRepository.findById(id);
    if (experiment.isEmpty()) {
      throw new NotFoundException();
    }
    return experiment.get();
  }

  public ExperimentModel updateExperiment(long id, ExperimentUpdateRequest req)
      throws Exception {
    req.Validate(id);
    Optional<ExperimentModel> experiment = experimentRepository.findById(id);
    if (experiment.isEmpty()) {
      throw new NotFoundException();
    }
    experiment.get().setName(req.getName());
    experiment.get().setDescription(req.getDescription());
    return experimentRepository.save(experiment.get());
  }

  public ExperimentModel deleteExperiment(long id) throws Exception {
    Optional<ExperimentModel> experiment = experimentRepository.findById(id);
    if (experiment.isEmpty()) {
      throw new NotFoundException();
    }
    experimentRepository.delete(experiment.get());
    return experiment.get();
  }
}
