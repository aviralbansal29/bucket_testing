package com.abed.bucket_testing.variants;

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
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest Controller to control API relating to Variants
 */
@RestController
public class VariantController {

  @Autowired VariantService variantService;

  @RequestMapping(method = RequestMethod.POST, value = "/variants")
  public ResponseEntity<VariantModel>
  createVariant(@Valid @RequestBody VariantCreateRequest variantReq)
      throws Exception {
    return new ResponseEntity<VariantModel>(
        variantService.createVariant(variantReq), HttpStatus.CREATED);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/variants/{id}")
  public ResponseEntity<VariantModel> getVariant(@PathVariable("id") long id) {
    try {
      return new ResponseEntity<VariantModel>(
          variantService.retrienveVariant(id), HttpStatus.OK);
    } catch (NotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/variants")
  public ResponseEntity<List<VariantModel>> listVariants() {
    return new ResponseEntity<List<VariantModel>>(variantService.listVariants(),
                                                  HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/variants/{id}")
  public ResponseEntity<VariantModel>
  updateVariant(@PathVariable("id") long id,
                @Valid @RequestBody VariantUpdateRequest req) throws Exception {
    return new ResponseEntity<VariantModel>(
        variantService.updateVariant(id, req), HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/variants/{id}")
  public ResponseEntity<VariantModel>
  deleteVariant(@PathVariable("id") long id) {
    return new ResponseEntity<VariantModel>(variantService.deleteVariant(id),
                                            HttpStatus.NO_CONTENT);
  }
}
