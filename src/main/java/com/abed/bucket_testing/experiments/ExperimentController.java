package com.abed.bucket_testing.experiments;

import com.abed.bucket_testing.dto.ListServiceResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExperimentController {

  @Autowired
  ExperimentService service;

  @RequestMapping(method = RequestMethod.POST, value = "/experiments")
  public ResponseEntity<ExperimentModel> createExperiment(@Valid @RequestBody ExperimentCreateRequest ex)
      throws Exception {
    return new ResponseEntity<ExperimentModel>(service.createExperiment(ex),
        HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/experiments")
  public ResponseEntity<List<ExperimentModel>> getExperiments(
      @RequestParam(name = "name", required = false) String nameSubString,
      @RequestParam(name = "is_published", required = false) Boolean isPublished) {
    ListServiceResponse details = service.listExperiments(nameSubString, isPublished);
    return new ResponseEntity<List<ExperimentModel>>(details.getContent(),
        HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/experiments/{id}")
  public ResponseEntity<ExperimentModel> retrieveExperiment(@PathVariable("id") long id) throws Exception {
    try {
      return new ResponseEntity<>(service.retrieveExperiment(id),
          HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      throw e;
    }
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/experiments/{id}")
  public ResponseEntity<ExperimentModel> updateExperiment(@PathVariable long id,
      @Valid @RequestBody ExperimentUpdateRequest req)
      throws Exception {
    try {
      return new ResponseEntity<ExperimentModel>(
          service.updateExperiment(id, req), HttpStatus.OK);
    } catch (NotFoundException n) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      throw e;
    }
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/experiments/{id}")
  public ResponseEntity<ExperimentModel> deleteExperiment(@PathVariable("id") long id) throws Exception {
    try {
      return new ResponseEntity<ExperimentModel>(service.deleteExperiment(id),
          HttpStatus.OK);
    } catch (NotFoundException n) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      throw e;
    }
  }
}
