// package
// ca.mcgillcssa.cssabackend.ControllerTests.MembershipBenefitControllerTests;

// import static org.mockito.ArgumentMatchers.anyList;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.when;
// import java.util.ArrayList;
// import java.util.Arrays;
// import org.springframework.http.MediaType;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.servlet.MockMvc;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import ca.mcgillcssa.cssabackend.controller.MembershipBenefitController;
// import
// ca.mcgillcssa.cssabackend.controller.MembershipBenefitController.MembershipBenefitRequestBody;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import ca.mcgillcssa.cssabackend.model.MembershipBenefit;
// import ca.mcgillcssa.cssabackend.service.MembershipBenefitService;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @ExtendWith(SpringExtension.class)
// @WebMvcTest(MembershipBenefitController.class)
// // layer actually used: controller layer, web layer, http request
// public class CreateMembershipBenefitControllerTest {

// @Autowired
// private MockMvc mockMvc;

// @MockBean
// private MembershipBenefitService membershipBenefitService;

// @Test
// public void createMembershipBenefit_ReturnsOk() throws Exception {
// // Arrange
// MembershipBenefit benefit = new MembershipBenefit(
// "merchantNameValue", "stripeUrlValue", "merchantTypeValue",
// "merchantAlternativeNameValue", new ArrayList<>(), "merchantAddressValue",
// "merchantPhoneValue", "merchantOpeningHoursValue", "merchantDiscountValue",
// "merchantPaymentMethodsValue");

// // populate benefit with test data if necessary
// when(membershipBenefitService.createMembershipBenefit(anyString(),
// anyString(), anyString(), anyString(),
// anyString(), anyString(), anyString(), anyString(), anyList(), anyString()))
// .thenReturn(benefit);

// MembershipBenefitRequestBody requestBody = new
// MembershipBenefitRequestBody();
// requestBody.setMerchantName("TestMerchant");
// requestBody.setStripeUrl("https://teststripeurl.com");
// requestBody.setMerchantType("TestType");
// requestBody.setMerchantAlternativeName("TestAlternativeName");
// requestBody.setMerchantImagesUrl(Arrays.asList("https://testimageurl1.com",
// "https://testimageurl2.com"));
// requestBody.setMerchantAddress("123 Test Street, Test City, Test Country");
// requestBody.setMerchantPhone("+123456789");
// requestBody.setMerchantOpeningHours("9:00AM - 5:00PM");
// requestBody.setMerchantDiscount("10% off");
// requestBody.setMerchantPaymentMethods("Visa, Mastercard");
// ObjectMapper objectMapper = new ObjectMapper();
// String requestBodyJson = objectMapper.writeValueAsString(requestBody);
// // Act & Assert
// mockMvc.perform(post("/membershipBenefits/")
// .contentType(MediaType.APPLICATION_JSON)
// .content(requestBodyJson)) // insert a JSON representation of
// MembershipBenefitRequestBody
// .andExpect(status().isOk())
// .andExpect(content().contentType(MediaType.APPLICATION_JSON))
// .andExpect(jsonPath("$.message").value("Membership Benefit created"))
// .andExpect(jsonPath("$.membershipBenefit.merchantName").value("merchantNameValue"))
// .andExpect(jsonPath("$.membershipBenefit.stripeUrl").value("stripeUrlValue"))
// .andExpect(jsonPath("$.membershipBenefit.merchantType").value("merchantTypeValue"))
// .andExpect(jsonPath("$.membershipBenefit.merchantAlternativeName").value("merchantAlternativeNameValue"))
// .andExpect(jsonPath("$.membershipBenefit.merchantAddress").value("merchantAddressValue"))
// .andExpect(jsonPath("$.membershipBenefit.merchantPhone").value("merchantPhoneValue"))
// .andExpect(jsonPath("$.membershipBenefit.merchantOpeningHours").value("merchantOpeningHoursValue"))
// .andExpect(jsonPath("$.membershipBenefit.merchantDiscount").value("merchantDiscountValue"))
// .andExpect(jsonPath("$.membershipBenefit.merchantPaymentMethods").value("merchantPaymentMethodsValue"));
// }
// // ... other tests, e.g., for BAD_REQUEST and INTERNAL_SERVER_ERROR
// responses.
// }
