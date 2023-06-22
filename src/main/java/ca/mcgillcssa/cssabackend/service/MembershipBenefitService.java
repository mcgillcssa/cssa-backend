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

  public MembershipBenefit createMembershipBenefit(String merchantName, String merchantBackgroundImageUrl,
      String merchantDiscount, String merchantLogoUrl, String merchantType) {
    if (merchantName == null || merchantBackgroundImageUrl == null || merchantDiscount == null
        || merchantLogoUrl == null || merchantType == null) {
      throw new IllegalArgumentException(
          "Missing information: merchantName, merchantBackgroundImageUrl, merchantDiscount, merchantLogoUrl and merchantType is required.");
    }

    try {
      if (merchantLogoUrl != null && !merchantLogoUrl.isEmpty()) {
        UrlChecker urlChecker = UrlChecker.isValidUrl(merchantLogoUrl);
        if (!urlChecker.isValid()) {
          throw new IllegalArgumentException("event image url connection failed");
        }
        merchantLogoUrl = urlChecker.getUrl();
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("merchantLogoUrl format error");
    }

    MerchantType merchantTypeEnum;
    try {
      merchantTypeEnum = MerchantType.valueOf(merchantType);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("merchantType is not valid");
    }

    MembershipBenefit newMembershipBenefit = new MembershipBenefit(merchantName, merchantBackgroundImageUrl,
        merchantDiscount, merchantLogoUrl, merchantTypeEnum);

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

  public boolean updateMembershipBenefitByMerchantName(String merchantName,
      String newMerchantName, String newMerchantBackgroundImageUrl, String newMerchantDiscount,
      String newMerchantLogoUrl, String newMerchantType) {
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
      if (newMerchantBackgroundImageUrl != null
          && !newMerchantBackgroundImageUrl.equals(membershipBenefit.getMerchantBackgroundImageUrl())) {
        membershipBenefit.setMerchantBackgroundImageUrl(newMerchantBackgroundImageUrl);
        hasChanges = true;
      }
      if (newMerchantDiscount != null && !newMerchantDiscount.equals(membershipBenefit.getMerchantDiscount())) {
        membershipBenefit.setMerchantDiscount(newMerchantDiscount);
        hasChanges = true;
      }

      if (newMerchantLogoUrl != null && !newMerchantLogoUrl.equals(membershipBenefit.getMerchantLogoUrl())) {
        try {
          if (newMerchantLogoUrl != null && !newMerchantLogoUrl.isEmpty()) {
            UrlChecker urlChecker = UrlChecker.isValidUrl(newMerchantLogoUrl);
            if (!urlChecker.isValid()) {
              throw new IllegalArgumentException("event image url connection failed");
            }
            newMerchantLogoUrl = urlChecker.getUrl();
            membershipBenefit.setMerchantLogoUrl(newMerchantLogoUrl);
            hasChanges = true;
          }
        } catch (Exception e) {
          throw new IllegalArgumentException("merchantLogoUrl format error");
        }
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
