package ca.mcgillcssa.cssabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import ca.mcgillcssa.cssabackend.model.MembershipBenefit;

@Data
@AllArgsConstructor

public class MembershipBenefitDTO {
  private String merchantName;
  private String merchantAlternativeName;
  private String stripeUrl;
  private String merchantType;

  private List<String> merchantImagesUrl;
  private String merchantAddress;
  private String merchantPhone;
  private String merchantOpeningHours;
  private String merchantDiscount;
  private String merchantPaymentMethods;

  public MembershipBenefitDTO(MembershipBenefit membershipBenefit) {
    this.merchantName = membershipBenefit.getMerchantName();
    this.stripeUrl = membershipBenefit.getStripeUrl();
    this.merchantDiscount = membershipBenefit.getMerchantDiscount();
    this.merchantType = membershipBenefit.getMerchantType().toString();
    this.merchantAlternativeName = membershipBenefit.getMerchantAlternativeName();
    this.merchantImagesUrl = membershipBenefit.getMerchantImagesUrl();
    this.merchantAddress = membershipBenefit.getMerchantAddress();
    this.merchantPhone = membershipBenefit.getMerchantPhone();
    this.merchantPaymentMethods = membershipBenefit.getMerchantPaymentMethods();
    this.merchantOpeningHours = membershipBenefit.getMerchantOpeningHours();
  }
}
