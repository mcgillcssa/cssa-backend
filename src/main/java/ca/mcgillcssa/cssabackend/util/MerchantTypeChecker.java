package ca.mcgillcssa.cssabackend.util;

import java.util.Arrays;
import java.util.List;

public class MerchantTypeChecker {

  private MerchantTypeChecker() {
    // Private constructor to prevent instantiation
  }

  public static boolean isValidMerchantType(String type) {
    List<String> validTypes = Arrays.asList("餐厅 Restaurants", "甜点饮品 Desserts & Drinks", "生活娱乐 Life & Entertainment", "购物 Shopping", "超值折扣 Special Offers", "其他 Others");
    return validTypes.contains(type);
  }
}
