package ca.mcgillcssa.cssabackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ca.mcgillcssa.cssabackend.model.MerchantCategory;
import ca.mcgillcssa.cssabackend.service.MerchantCategoryService;
import lombok.Data;
import lombok.ToString;

@RestController
@RequestMapping("/merchantCategories")
public class MerchantCategoryController {
  @Autowired
  private MerchantCategoryService merchantCategoryService;

  @PostMapping
  public ResponseEntity<?> createMerchantCategory(@RequestBody MerchantCategoryRequestBody requestBody) {
    Map<String, Object> response = new HashMap<>();

    try {
      MerchantCategory created = merchantCategoryService.createMerchantCategory(
          requestBody.getMerchantType(),
          requestBody.getMerchantCategoryUrl());

      response.put("message", "Merchant Category created successfully");
      response.put("merchantCategory", created);
      return ResponseEntity.status(HttpStatus.CREATED).body(response);

    } catch (IllegalArgumentException e) {
      response.put("message", "Failed to create merchant category.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    } catch (Exception e) { // Catch-all for other exceptions
      response.put("message", "Failed to create merchant category. Internal error.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @GetMapping("/{merchantType}")
  public ResponseEntity<?> getByMerchantType(@PathVariable String merchantType) {
    Map<String, Object> response = new HashMap<>();

    return merchantCategoryService.findByMerchantType(merchantType)
        .map(merchantCategory -> {
          response.put("message", "Merchant Category retrieved successfully");
          response.put("merchantCategory", merchantCategory);
          return ResponseEntity.status(HttpStatus.OK).body(response);
        })
        .orElseGet(() -> {
          response.put("message", "Merchant Category not found");
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        });
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllMerchantCategories() {
    Map<String, Object> response = new HashMap<>();
    List<MerchantCategory> categories = merchantCategoryService.findAll();

    if (categories != null && !categories.isEmpty()) {
      response.put("message", "Merchant Categories retrieved successfully");
      response.put("categories", categories);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "No Merchant Categories found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PutMapping("/{merchantType}")
  public ResponseEntity<?> updateMerchantCategory(@PathVariable String merchantType,
      @RequestBody MerchantCategoryRequestBody requestBody) {
    Map<String, Object> response = new HashMap<>();

    try {
      boolean updated = merchantCategoryService.updateMerchantCategory(merchantType,
          requestBody.getMerchantCategoryUrl());

      if (updated) {
        response.put("message", "Merchant Category updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
      } else {
        response.put("message", "Merchant Category update failed. No such Category found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }
    } catch (IllegalArgumentException e) {
      response.put("message", "Failed to update merchant category.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    } catch (Exception e) { // Catch-all for other exceptions
      response.put("message", "Failed to update merchant category. Internal error.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @DeleteMapping("/{merchantType}")
  public ResponseEntity<?> deleteByMerchantType(@PathVariable String merchantType) {
    Map<String, Object> response = new HashMap<>();

    if (merchantCategoryService.deleteByMerchantType(merchantType)) {
      response.put("message", "Merchant Category deleted successfully");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "Merchant Category not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @DeleteMapping("/all")
  public ResponseEntity<?> deleteAll() {
    Map<String, Object> response = new HashMap<>();

    if (merchantCategoryService.deleteAll()) {
      response.put("message", "All Merchant Categories deleted successfully");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "Failed to delete Merchant Categories. No categories to delete.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @Data
  @ToString
  public static class MerchantCategoryRequestBody {
    private String merchantType;
    private String merchantCategoryUrl;

    // Getters and setters will be generated by Lombok
  }
}
