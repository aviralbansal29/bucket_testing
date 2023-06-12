package com.abed.bucket_testing.variants;

import com.abed.bucket_testing.dto.ListServiceResponse;
import com.abed.bucket_testing.exceptions.InvalidRequestException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

/**
 * Defines Business Logic related to Variants
 */
@Service
public class VariantService {

  @Autowired
  VariantRepository variantRepository;

  public void createControlVariant(long id) {
    VariantModel variant = new VariantModel(
        "control", "Auto-created control variant", (byte) 100, id);
    variantRepository.save(variant);
  }

  public VariantModel createVariant(VariantCreateRequest variantReq)
      throws InvalidRequestException {
    variantReq.Validate();
    VariantModel variant = new VariantModel(
        variantReq.getName(), variantReq.getDescription(),
        variantReq.getWeightage(), variantReq.getExperiment_id());
    variantRepository.save(variant);
    variantReq.getControlVariant().setWeightage(
        (byte) (variantReq.getControlVariant().getWeightage() -
            variantReq.getWeightage()));
    variantRepository.save(variantReq.getControlVariant());
    return variant;
  }

  public ListServiceResponse<VariantModel> listVariants(String query,
      Long experimentId) {
    List<VariantModel> variantList = variantRepository.findByCriteria(query, experimentId);
    return new ListServiceResponse<>(variantList.size(), variantList);
  }

  public VariantModel retrienveVariant(long id) throws Exception {
    Optional<VariantModel> variant = variantRepository.findById(id);
    if (variant.isEmpty()) {
      throw new NotFoundException();
    }
    return variant.get();
  }

  public VariantModel updateVariant(long id, VariantUpdateRequest req)
      throws Exception {
    req.Validate(id);
    VariantModel variant = req.getCurrentVariant();
    variant.setDescription(req.getDescription());
    variant.setName(req.getName());
    variant.setWeightage(req.getWeightage());
    variantRepository.save(variant);
    req.getControlVariant().setWeightage(
        (byte) (req.getControlVariant().getWeightage() - req.getWeightage()));
    variantRepository.save(req.getControlVariant());
    return variant;
  }

  public VariantModel deleteVariant(long id) throws Exception {
    Optional<VariantModel> variant = variantRepository.findById(id);
    if (variant.isEmpty()) {
      throw new NotFoundException();
    }
    variantRepository.deleteById(id);
    return variant.get();
  }
}
