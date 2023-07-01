package ca.mcgillcssa.cssabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ca.mcgillcssa.cssabackend.model.MembershipBenefit;

@Data
@AllArgsConstructor

public class MembershipBenefitDTO {
  private String merchantName;
  private String merchantBackgroundImageUrl;
  private String merchantDiscount;
  private String merchantLogoUrl;
  private String merchantType;

  public MembershipBenefitDTO(MembershipBenefit membershipBenefit) {
    this.merchantName = membershipBenefit.getMerchantName();
    this.merchantBackgroundImageUrl = membershipBenefit.getMerchantBackgroundImageUrl();
    this.merchantDiscount = membershipBenefit.getMerchantDiscount();
    this.merchantLogoUrl = membershipBenefit.getMerchantLogoUrl();
    this.merchantType = membershipBenefit.getMerchantType().toString();
  }
}
