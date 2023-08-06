package ca.mcgillcssa.cssabackend.util;

import java.util.Arrays;
import java.util.List;

public class MerchantTypeChecker {

  private MerchantTypeChecker() {
    // Private constructor to prevent instantiation
  }

  public static boolean isValidMerchantType(String type) {
    List<String> validTypes = Arrays.asList("RESTAURANT", "SWEETS", "SHOPPING", "BEAUTY", "OTHER");
    return validTypes.contains(type);
  }
}
