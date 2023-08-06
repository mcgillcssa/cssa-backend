package ca.mcgillcssa.cssabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ca.mcgillcssa.cssabackend.model.MerchantCategory;

@Data
@AllArgsConstructor
public class MerchantCategoryDTO {
  private String merchantType;
  private String merchantCategoryUrl;

  public MerchantCategoryDTO(MerchantCategory merchantCategory) {
    this.merchantType = merchantCategory.getMerchantType();
    this.merchantCategoryUrl = merchantCategory.getMerchantCategoryUrl();
  }
}
