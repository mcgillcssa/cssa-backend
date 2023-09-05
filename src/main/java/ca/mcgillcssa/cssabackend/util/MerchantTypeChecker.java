package ca.mcgillcssa.cssabackend.util;

import java.util.Arrays;
import java.util.List;

public class MerchantTypeChecker {

  private MerchantTypeChecker() {
    // Private constructor to prevent instantiation
  }

  public static boolean isValidMerchantType(String type) {
    List<String> validTypes = Arrays.asList("主食，餐厅", "甜品，饮品", "生活，娱乐", "购物", "其他");
    return validTypes.contains(type);
  }
}
