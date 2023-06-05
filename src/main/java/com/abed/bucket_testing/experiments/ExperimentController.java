package com.abed.bucket_testing.experiments;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExperimentController {

  @Autowired ExperimentRepository exRepo;

  @RequestMapping(method = RequestMethod.GET, value = "/experiments")
  public ResponseEntity<List<ExperimentModel>> getExperiments() {
    List<ExperimentModel> exps = new ArrayList<ExperimentModel>();
    exRepo.findAll().forEach(exps::add);
    return new ResponseEntity<>(exps, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/experiments/{id}")
  public ResponseEntity<ExperimentModel>
  retrieveExperiment(@PathVariable("id") long id) {
    Optional<ExperimentModel> exp = exRepo.findById(id);
    if (exp.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<ExperimentModel>(exp.get(), HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, value = "/experiments")
  public ExperimentModel
  createExperiment(@Valid @RequestBody ExperimentCreateRequest ex) {
    ExperimentModel experiment =
        exRepo.save(new ExperimentModel(ex.getName(), ex.getDescription()));
    return experiment;
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/experiments/{id}")
  public ResponseEntity<HttpStatus>
  deleteExperiment(@PathVariable("id") long id) {
    exRepo.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
