package ca.mcgillcssa.cssabackend.model;

import java.util.List;
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
  private String merchantImageUrl;
  private MerchantType merchantType;

  private String merchantAlternativeName;
  private List<String> merchantImagesUrl;
  private String merchantAddress;
  private String merchantPhone;
  private String merchantOpeningHours;
  private String merchantDiscount;
  private String merchantPaymentMethods;

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
