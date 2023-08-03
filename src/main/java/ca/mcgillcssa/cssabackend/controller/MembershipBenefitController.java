package ca.mcgillcssa.cssabackend.controller;

import ca.mcgillcssa.cssabackend.dto.MembershipBenefitDTO;
import ca.mcgillcssa.cssabackend.model.MembershipBenefit;
import ca.mcgillcssa.cssabackend.service.MembershipBenefitService;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Data;
import lombok.ToString;

@RestController
@RequestMapping("/membershipBenefits")
public class MembershipBenefitController {
  private final MembershipBenefitService membershipBenefitService;

  public MembershipBenefitController(MembershipBenefitService membershipBenefitService) {
    this.membershipBenefitService = membershipBenefitService;
  }

  @PostMapping("/")
  public ResponseEntity<?> createMembershipBenefit(@RequestBody MembershipBenefitRequestBody requestBody) {
    Map<String, Object> response = new HashMap<>();
    try {
      MembershipBenefit newBenefit = membershipBenefitService.createMembershipBenefit(
          requestBody.getMerchantName(),
          requestBody.getStripeUrl(),
          requestBody.getMerchantDiscount(),
          requestBody.getMerchantType(),
          requestBody.getMerchantAddress(),
          requestBody.getMerchantPhone(),
          requestBody.getMerchantOpeningHours(),
          requestBody.getMerchantPaymentMethods(),
          requestBody.getMerchantImagesUrl(),
          requestBody.getMerchantAlternativeName());

      response.put("message", "Membership Benefit created");
      response.put("membershipBenefit", new MembershipBenefitDTO(newBenefit));
      return ResponseEntity.status(HttpStatus.OK).body(response);

    } catch (IllegalArgumentException e) {
      response.put("message", "Failed to create membership benefit.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (DataAccessException e) {
      response.put("message", "Failed to create membership benefit. Database access error.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @GetMapping("/name/{merchantName}")
  public ResponseEntity<?> getMembershipBenefitByMerchantName(@PathVariable String merchantName) {
    Optional<MembershipBenefit> optionalBenefit = membershipBenefitService
        .findMembershipBenefitByMerchantName(merchantName);
    Map<String, Object> response = new HashMap<>();
    if (optionalBenefit.isPresent()) {
      MembershipBenefit benefit = optionalBenefit.get();
      response.put("message", "Membership Benefit found with merchant name " + merchantName);
      response.put("membershipBenefit", new MembershipBenefitDTO(benefit));
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "Membership Benefit not found with merchant name " + merchantName);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @PutMapping("/name/{merchantName}")
  public ResponseEntity<?> updateMembershipBenefitByMerchantName(@PathVariable String merchantName,
      @RequestBody MembershipBenefitRequestBody requestBody) {
    Map<String, Object> response = new HashMap<>();
    try {
      boolean updated = membershipBenefitService.updateMembershipBenefitByMerchantName(
          merchantName,
          requestBody.getMerchantName(),
          requestBody.getStripeUrl(),
          requestBody.getMerchantDiscount(),
          requestBody.getMerchantType(),
          requestBody.getMerchantAddress(),
          requestBody.getMerchantPhone(),
          requestBody.getMerchantOpeningHours(),
          requestBody.getMerchantPaymentMethods(),
          requestBody.getMerchantImagesUrl(),
          requestBody.getMerchantAlternativeName());

      if (updated) {
        response.put("message", "Membership Benefit updated successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
      } else {
        response.put("message", "Membership Benefit update failed. No such benefit found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      }

    } catch (IllegalArgumentException e) {
      response.put("message", "Failed to update membership benefit.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (DataAccessException e) {
      response.put("message", "Failed to update membership benefit. Database access error.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllMembershipBenefits() {
    List<MembershipBenefit> benefits = membershipBenefitService.findAll();
    Map<String, Object> response = new HashMap<>();
    if (!benefits.isEmpty()) {
      response.put("message", "All membership benefits retrieved successfully");
      response.put("membershipBenefits", benefits);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "No membership benefits found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @GetMapping("/allByMerchantType")
  public ResponseEntity<?> getAllByMerchantType() {
    Map<MembershipBenefit.MerchantType, List<MembershipBenefit>> benefitsByType = membershipBenefitService
        .getAllByMerchantType();
    Map<String, Object> response = new HashMap<>();

    if (!benefitsByType.isEmpty()) {
      response.put("message", "All benefits retrieved successfully");
      response.put("benefitsByType", benefitsByType);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "No benefits found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @DeleteMapping("/name/{merchantName}")
  public ResponseEntity<?> deleteMembershipBenefitByMerchantName(@PathVariable String merchantName) {
    Map<String, Object> response = new HashMap<>();
    boolean deleted = membershipBenefitService.deleteMembershipBenefitByMerchantName(merchantName);
    if (deleted) {
      response.put("message", "Membership Benefit with merchant name " + merchantName + " successfully deleted");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    response.put("message", "Membership Benefit with merchant name " + merchantName + " not found");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @DeleteMapping("/all")
  public ResponseEntity<?> deleteAllMembershipBenefits() {
    boolean deleted = membershipBenefitService.deleteAll();
    if (deleted) {
      return ResponseEntity.ok("All membership benefits deleted successfully");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No membership benefits found");
  }

  @Data
  @ToString
  public static class MembershipBenefitRequestBody {
    private String merchantName;
    private String stripeUrl;
    private String merchantType;

    private String merchantAlternativeName;
    private List<String> merchantImagesUrl;
    private String merchantAddress;
    private String merchantPhone;
    private String merchantOpeningHours;
    private String merchantDiscount;
    private String merchantPaymentMethods;

    // Getters and setters
  }
}
