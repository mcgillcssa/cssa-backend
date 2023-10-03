package ca.mcgillcssa.cssabackend.ServiceTests.MembershipBenefitServiceTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import ca.mcgillcssa.cssabackend.model.MembershipBenefit;
import ca.mcgillcssa.cssabackend.repository.MemebershipBenefitRepository;
import ca.mcgillcssa.cssabackend.service.MembershipBenefitService;

@RunWith(MockitoJUnitRunner.class)
public class CreateMembershipBenefitServiceTest {

  @InjectMocks
  private MembershipBenefitService membershipBenefitService;

  @Mock
  private MemebershipBenefitRepository membershipBenefitRepository;

  @BeforeEach
  public void setupEach() {
    MockitoAnnotations.initMocks(this);
    // Reset the repository mock before each test
    Mockito.reset(membershipBenefitRepository);
  }

  @Test
  public void createMembershipBenefit_MissingParameter_ThrowsIllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () -> {
      membershipBenefitService.createMembershipBenefit(null, "validUrl", "discount", "type", "address", "phone",
          "hours", "payment", new ArrayList<>(), "alternativeName");
    });
  }

  @Test
  public void
  createMembershipBenefit_AlreadyExistingMerchant_ThrowsIllegalArgumentException()
  {
    when(membershipBenefitRepository.findMembershipBenefitByMerchantName("existingMerchant")).thenReturn(Optional.of(new
    MembershipBenefit( "merchantName",
        "https://i.imgur.com/Cqv3xm6.png",
        "主食，餐厅",
        "AlternativeMerchantName",
        Arrays.asList("https://i.imgur.com/Cqv3xm6.png"),
        "123 Main Street",
        "555-555-5555",
        "9am-5pm",
        "10% off",
        "Visa, Mastercard")));

    assertThrows(IllegalArgumentException.class, () -> {
    membershipBenefitService.createMembershipBenefit("existingMerchant",
    "validUrl", "discount", "type", "address", "phone", "hours", "payment", new
    ArrayList<>(), "alternativeName");
    });
  }

  @Test
  public void createMembershipBenefit_InvalidStripeUrl_ThrowsIllegalArgumentException() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      membershipBenefitService.createMembershipBenefit("merchantName", "invalidUrl", "discount", "type", "address",
          "phone", "hours", "payment", new ArrayList<>(), "alternativeName");
    });

    String expectedMessage = "merchant image url format error";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  public void createMembershipBenefit_InvalidMerchantType_ThrowsIllegalArgumentException() {
    // You might have to mock MerchantTypeChecker.isValidMerchantType() to return
    // false.

    assertThrows(IllegalArgumentException.class, () -> {
      membershipBenefitService.createMembershipBenefit("merchantName", "validUrl", "discount", "invalidType", "address",
          "phone", "hours", "payment", new ArrayList<>(), "alternativeName");
    });
  }

  @Test
  public void createMembershipBenefit_ValidInputs_ReturnsBenefit() {
    MembershipBenefit benefit = new MembershipBenefit(
        "merchantName",
        "https://i.imgur.com/Cqv3xm6.png",
        "主食，餐厅",
        "AlternativeMerchantName",
        Arrays.asList("https://i.imgur.com/Cqv3xm6.png"),
        "123 Main Street",
        "555-555-5555",
        "9am-5pm",
        "10% off",
        "Visa, Mastercard");

    when(membershipBenefitRepository.createMemebershipBenefit(any(MembershipBenefit.class))).thenReturn(benefit);

    MembershipBenefit result = membershipBenefitService.createMembershipBenefit(
        benefit.getMerchantName(),
        benefit.getStripeUrl(),
        benefit.getMerchantDiscount(),
        benefit.getMerchantType(),
        benefit.getMerchantAddress(),
        benefit.getMerchantPhone(),
        benefit.getMerchantOpeningHours(),
        benefit.getMerchantPaymentMethods(),
        benefit.getMerchantImagesUrl(),
        benefit.getMerchantAlternativeName());

    // Assertions
    assertNotNull(result);
    assertEquals(benefit.getMerchantName(), result.getMerchantName());
    assertEquals(benefit.getStripeUrl(), result.getStripeUrl());
    assertEquals(benefit.getMerchantDiscount(), result.getMerchantDiscount());
    assertEquals(benefit.getMerchantType(), result.getMerchantType());
    assertEquals(benefit.getMerchantAddress(), result.getMerchantAddress());
    assertEquals(benefit.getMerchantPhone(), result.getMerchantPhone());
    assertEquals(benefit.getMerchantOpeningHours(), result.getMerchantOpeningHours());
    assertEquals(benefit.getMerchantPaymentMethods(), result.getMerchantPaymentMethods());
    assertEquals(benefit.getMerchantAlternativeName(), result.getMerchantAlternativeName());
    assertArrayEquals(benefit.getMerchantImagesUrl().toArray(), result.getMerchantImagesUrl().toArray());
  }
}
