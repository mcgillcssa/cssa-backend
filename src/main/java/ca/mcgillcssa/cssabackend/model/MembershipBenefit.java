package ca.mcgillcssa.cssabackend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Document(collection = "benefits")
@Data
@AllArgsConstructor
@ToString
public class MembershipBenefit {
  private String merchantName;
  private String merchantBackgroundImageUrl;
  private String merchantDiscount;
  private String merchantLogoUrl;
  private MerchantType merchantType;

  public enum MerchantType {
    RESTAURANT,
    SWEETS,
    SHOPPING,
    BEAUTY,
    OTHER;

    @Override
    public String toString() {
      return name();
    }
  }
}
