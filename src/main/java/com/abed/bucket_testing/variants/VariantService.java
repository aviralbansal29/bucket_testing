package com.abed.bucket_testing.variants;

import com.abed.bucket_testing.exceptions.InvalidRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

/**
 * Defines Business Logic related to Variants
 */
@Service
public class VariantService {

  @Autowired VariantRepository variantRepo;

  private void validateUniqueNameForExperiment(VariantCreateRequest variantReq)
      throws InvalidRequestException {
    List<VariantModel> variants = variantRepo.findByExperimentIdAndName(
        variantReq.getExperiment_id(), variantReq.getName());
    if (variants.size() > 0) {
      InvalidRequestException exception = new InvalidRequestException();
      exception.addError(
          new FieldError("VariantCreateRequest", "name", "Duplicate Entry."));

      throw exception;
    }
  }

  private void validateUniqueNameForExperiment(VariantModel variant,
                                               VariantUpdateRequest req)
      throws InvalidRequestException {
    List<VariantModel> variants = variantRepo.findByExperimentIdAndNameAndNotId(
        variant.getExperiment_id(), req.getName(), variant.getId());
    if (variants.size() > 0) {
      InvalidRequestException exception = new InvalidRequestException();
      exception.addError(
          new FieldError("VariantUpdateRequest", "name", "Duplicate Entry."));

      throw exception;
    }
  }

  public VariantModel createVariant(VariantCreateRequest variantReq)
      throws InvalidRequestException {
    validateUniqueNameForExperiment(variantReq);
    VariantModel variant =
        new VariantModel(variantReq.getName(), variantReq.getDescription(),
                         (byte)0, variantReq.getExperiment_id());
    variantRepo.save(variant);
    return variant;
  }

  public List<VariantModel> listVariants() {
    List<VariantModel> variantList = new ArrayList<VariantModel>();
    variantRepo.findAll().forEach(variantList::add);
    return variantList;
  }

  public VariantModel retrienveVariant(long id) throws NotFoundException {
    Optional<VariantModel> variant = variantRepo.findById(id);
    if (variant.isEmpty()) {
      throw new NotFoundException();
    }
    return variant.get();
  }

  public VariantModel updateVariant(long id, VariantUpdateRequest req)
      throws Exception {
    Optional<VariantModel> variant = variantRepo.findById(id);
    if (variant.isEmpty()) {
      throw new NotFoundException();
    }
    validateUniqueNameForExperiment(variant.get(), req);
    variant.get().setDescription(req.getDescription());
    variant.get().setName(req.getName());
    variantRepo.save(variant.get());
    return variant.get();
  }

  public VariantModel deleteVariant(long id) {
    Optional<VariantModel> variant = variantRepo.findById(id);
    if (variant.isPresent()) {
      variantRepo.delete(variant.get());
    }
    return variant.get();
  }
}
