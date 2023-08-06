package ca.mcgillcssa.cssabackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ca.mcgillcssa.cssabackend.model.MerchantCategory;
import ca.mcgillcssa.cssabackend.repository.MerchantCategoryRepository;
import ca.mcgillcssa.cssabackend.util.MerchantTypeChecker;

@Service
public class MerchantCategoryService {

  private final MerchantCategoryRepository merchantCategoryRepository;

  public MerchantCategoryService(MerchantCategoryRepository merchantCategoryRepository) {
    this.merchantCategoryRepository = merchantCategoryRepository;
  }

  public MerchantCategory createMerchantCategory(String merchantType, String merchantCategoryUrl) {
    if (merchantType == null || merchantCategoryUrl == null) {
      throw new IllegalArgumentException("Merchant type and merchant category URL are required.");
    }

    if (findByMerchantType(merchantType).isPresent()) {
      throw new IllegalArgumentException("A merchant category with the type " + merchantType + " already exists.");
    }

    if (!MerchantTypeChecker.isValidMerchantType(merchantType)) {
      throw new IllegalArgumentException(
          "Invalid merchant Type. Valid types are: RESTAURANT,SWEETS,SHOPPING,BEAUTY,OTHER");
    }

    MerchantCategory newMerchantCategory = new MerchantCategory(merchantType, merchantCategoryUrl);
    return merchantCategoryRepository.save(newMerchantCategory);
  }

  public Optional<MerchantCategory> findByMerchantType(String merchantType) {
    return merchantCategoryRepository.findByMerchantType(merchantType);
  }

  public List<MerchantCategory> findAll() {
    return merchantCategoryRepository.findAll();
  }

  public boolean updateMerchantCategory(String merchantType, String merchantCategoryUrl) {
    MerchantCategory existingCategory = findByMerchantType(merchantType)
        .orElseThrow(
            () -> new IllegalArgumentException("Merchant category with type " + merchantType + " does not exist."));

    if (merchantCategoryUrl.equals(existingCategory.getMerchantCategoryUrl())) {
      throw new IllegalArgumentException("Nothing to be changed");
    }
    existingCategory.setMerchantCategoryUrl(merchantCategoryUrl);
    merchantCategoryRepository.deleteByMerchantType(merchantType);
    merchantCategoryRepository.save(existingCategory);
    return true;
  }

  public boolean deleteByMerchantType(String merchantType) {
    return merchantCategoryRepository.deleteByMerchantType(merchantType);
  }

  public boolean deleteAll() {
    return merchantCategoryRepository.deleteAll();
  }
}
