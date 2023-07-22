package ca.mcgillcssa.cssabackend.service;

import java.util.List;
import java.util.Optional;
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

  public MembershipBenefit createMembershipBenefit(String merchantName, String merchantImageUrl,
      String merchantDiscount, String merchantType, String merchantAddress, String merchantPhone,
      String merchantOpeningHours, String merchantPaymentMethods, List<String> merchantImagesUrl,
      String merchantAlternativeName) {
    if (merchantName == null || merchantImageUrl == null || merchantDiscount == null || merchantType == null
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
      if (merchantImageUrl != null && !merchantImageUrl.isEmpty()) {
        UrlChecker urlChecker = UrlChecker.isValidUrl(merchantImageUrl);
        if (!urlChecker.isValid()) {
          throw new IllegalArgumentException("merchant image url connection failed");
        }
        merchantImageUrl = urlChecker.getUrl();
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

    MembershipBenefit newMembershipBenefit = new MembershipBenefit(merchantName, merchantImageUrl,
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

  public boolean updateMembershipBenefitByMerchantName(String merchantName, String newMerchantName,
      String newMerchantImageUrl,
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

      if (newMerchantImageUrl != null
          && !newMerchantImageUrl.equals(membershipBenefit.getMerchantImageUrl())) {
        membershipBenefit.setMerchantImageUrl(newMerchantImageUrl);
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

      if (newMerchantImageUrl != null) {
        try {
          if (newMerchantImageUrl != null && !newMerchantImageUrl.isEmpty()) {
            UrlChecker urlChecker = UrlChecker.isValidUrl(newMerchantImageUrl);
            if (!urlChecker.isValid()) {
              throw new IllegalArgumentException("merchant image url connection failed");
            }
            newMerchantImageUrl = urlChecker.getUrl();
          }
        } catch (Exception e) {
          throw new IllegalArgumentException("merchant image url format error");
        }
        if (!newMerchantImageUrl.equals(membershipBenefit.getMerchantImageUrl())) {
          membershipBenefit.setMerchantImageUrl(newMerchantImageUrl);
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
