package ca.mcgillcssa.cssabackend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;

import org.springframework.stereotype.Service;
import ca.mcgillcssa.cssabackend.model.MembershipBenefit;
import ca.mcgillcssa.cssabackend.model.MembershipBenefit.MerchantType;
import ca.mcgillcssa.cssabackend.repository.MemebershipBenefitRepository;
import ca.mcgillcssa.cssabackend.util.UrlChecker;

@Service
public class MembershipBenefitService {
  private final MemebershipBenefitRepository memebershipBenefitRepository;

  public MembershipBenefitService(MemebershipBenefitRepository memebershipBenefitRepository) {
    this.memebershipBenefitRepository = memebershipBenefitRepository;
  }

  public MembershipBenefit createMembershipBenefit(String merchantName, String stripeUrl,
      String merchantDiscount, String merchantType, String merchantAddress, String merchantPhone,
      String merchantOpeningHours, String merchantPaymentMethods, List<String> merchantImagesUrl,
      String merchantAlternativeName) {
    if (merchantName == null || stripeUrl == null || merchantDiscount == null || merchantType == null
        || merchantAddress == null || merchantPhone == null || merchantPaymentMethods == null ||
        merchantImagesUrl == null || merchantOpeningHours == null || merchantAlternativeName == null) {
      throw new IllegalArgumentException(
          "Missing information: merchantName, merchantAddress, merchantPhone, merchantPaymentMethods, merchantBackgroundImageUrl, merchantDiscount, merchantAlternativeName, merchantOpeningHours, merchantImagesUrl and merchantType is required.");
    }

    Optional<MembershipBenefit> optionalMembershipBenefit = memebershipBenefitRepository
        .findMembershipBenefitByMerchantName(merchantName);

    if (optionalMembershipBenefit.isPresent()) {
      throw new IllegalArgumentException(
          "A Benefit with given name already exists");
    }

    MerchantType merchantTypeEnum;
    try {
      merchantTypeEnum = MerchantType.valueOf(merchantType);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("merchantType is not valid");
    }

    try {
      if (stripeUrl != null && !stripeUrl.isEmpty()) {
        UrlChecker urlChecker = UrlChecker.isValidUrl(stripeUrl);
        if (!urlChecker.isValid()) {
          throw new IllegalArgumentException("merchant image url connection failed");
        }
        stripeUrl = urlChecker.getUrl();
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("merchant image url format error");
    }

    for (String url : merchantImagesUrl) {
      try {
        if (url != null && !url.isEmpty()) {
          UrlChecker urlChecker = UrlChecker.isValidUrl(url);
          if (!urlChecker.isValid()) {
            throw new IllegalArgumentException("one of the merchant images url connection failed");
          }
          url = urlChecker.getUrl();
        }
      } catch (Exception e) {
        throw new IllegalArgumentException("One of the merchant images url format error");
      }
    }

    MembershipBenefit newMembershipBenefit = new MembershipBenefit(merchantName, stripeUrl,
        merchantTypeEnum, merchantAlternativeName, merchantImagesUrl, merchantAddress, merchantPhone,
        merchantOpeningHours,
        merchantDiscount, merchantPaymentMethods);

    return memebershipBenefitRepository.createMemebershipBenefit(newMembershipBenefit);
  }

  public Optional<MembershipBenefit> findMembershipBenefitByMerchantName(String merchantName) {
    return memebershipBenefitRepository.findMembershipBenefitByMerchantName(merchantName);
  }

  public List<MembershipBenefit> findAll() {
    return memebershipBenefitRepository.findAll();
  }

  public boolean deleteMembershipBenefitByMerchantName(String merchantName) {
    return memebershipBenefitRepository.deleteByName(merchantName);
  }

  public boolean deleteAll() {
    return memebershipBenefitRepository.deleteAll();
  }

  public Map<MerchantType, List<MembershipBenefit>> getAllByMerchantType() {
    List<MembershipBenefit> benefits = memebershipBenefitRepository.findAll();
    return benefits.stream()
        .collect(Collectors.groupingBy(MembershipBenefit::getMerchantType));
  }

  public boolean updateMembershipBenefitByMerchantName(String merchantName, String newMerchantName,
      String newStripeUrl,
      String newMerchantDiscount, String newMerchantType, String newMerchantAddress, String newMerchantPhone,
      String newMerchantOpeningHours, String newMerchantPaymentMethods, List<String> newMerchantImagesUrl,
      String newMerchantAlternativeName) {

    Optional<MembershipBenefit> optionalMembershipBenefit = memebershipBenefitRepository
        .findMembershipBenefitByMerchantName(merchantName);

    if (optionalMembershipBenefit.isPresent()) {
      MembershipBenefit membershipBenefit = optionalMembershipBenefit.get();
      // Check if any fields have changed
      boolean hasChanges = false;

      if (newMerchantName != null && !newMerchantName.equals(membershipBenefit.getMerchantName())) {
        membershipBenefit.setMerchantName(newMerchantName);
        hasChanges = true;
      }

      if (newMerchantAlternativeName != null
          && !newMerchantAlternativeName.equals(membershipBenefit.getMerchantAlternativeName())) {
        membershipBenefit.setMerchantAlternativeName(newMerchantAlternativeName);
        hasChanges = true;
      }

      if (newMerchantAddress != null
          && !newMerchantAddress.equals(membershipBenefit.getMerchantAddress())) {
        membershipBenefit.setMerchantAddress(newMerchantAddress);
        hasChanges = true;
      }

      if (newMerchantPhone != null
          && !newMerchantPhone.equals(membershipBenefit.getMerchantPhone())) {
        membershipBenefit.setMerchantPhone(newMerchantPhone);
        hasChanges = true;
      }

      if (newMerchantOpeningHours != null
          && !newMerchantOpeningHours.equals(membershipBenefit.getMerchantOpeningHours())) {
        membershipBenefit.setMerchantOpeningHours(newMerchantOpeningHours);
        hasChanges = true;
      }

      if (newStripeUrl != null
          && !newStripeUrl.equals(membershipBenefit.getStripeUrl())) {
        membershipBenefit.setStripeUrl(newStripeUrl);
        hasChanges = true;
      }
      if (newMerchantDiscount != null && !newMerchantDiscount.equals(membershipBenefit.getMerchantDiscount())) {
        membershipBenefit.setMerchantDiscount(newMerchantDiscount);
        hasChanges = true;
      }

      if (newMerchantType != null) {
        MerchantType merchantTypeEnum;
        try {
          merchantTypeEnum = MerchantType.valueOf(newMerchantType);
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("merchantType is not valid");
        }
        if (!merchantTypeEnum.equals(membershipBenefit.getMerchantType())) {
          membershipBenefit.setMerchantType(merchantTypeEnum);
          hasChanges = true;
        }
      }

      if (newStripeUrl != null) {
        try {
          if (newStripeUrl != null && !newStripeUrl.isEmpty()) {
            UrlChecker urlChecker = UrlChecker.isValidUrl(newStripeUrl);
            if (!urlChecker.isValid()) {
              throw new IllegalArgumentException("merchant image url connection failed");
            }
            newStripeUrl = urlChecker.getUrl();
          }
        } catch (Exception e) {
          throw new IllegalArgumentException("merchant image url format error");
        }
        if (!newStripeUrl.equals(membershipBenefit.getStripeUrl())) {
          membershipBenefit.setStripeUrl(newStripeUrl);
          hasChanges = true;
        }
      }

      if (newMerchantImagesUrl != null) {
        for (String url : newMerchantImagesUrl) {
          try {
            if (url != null && !url.isEmpty()) {
              UrlChecker urlChecker = UrlChecker.isValidUrl(url);
              if (!urlChecker.isValid()) {
                throw new IllegalArgumentException("one of the merchant images url connection failed");
              }
              url = urlChecker.getUrl();
            }
          } catch (Exception e) {
            throw new IllegalArgumentException("One of the merchant images url format error");
          }
        }
        membershipBenefit.setMerchantImagesUrl(newMerchantImagesUrl);
      }

      if (!hasChanges) {
        throw new IllegalArgumentException("Nothing to be changed");
      }

      memebershipBenefitRepository.deleteByName(merchantName);
      memebershipBenefitRepository.save(membershipBenefit);
      return true;
    } else {
      throw new IllegalArgumentException("Membership Benefit does not exist with merchant name: " + merchantName);
    }
  }
}
