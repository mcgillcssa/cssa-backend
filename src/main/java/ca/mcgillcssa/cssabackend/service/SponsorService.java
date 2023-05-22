package ca.mcgillcssa.cssabackend.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ca.mcgillcssa.cssabackend.model.Sponsor;
import ca.mcgillcssa.cssabackend.model.Sponsor.CoopDuration;
import ca.mcgillcssa.cssabackend.model.Sponsor.SponsorClass;
import ca.mcgillcssa.cssabackend.repository.SponsorRepository;

import ca.mcgillcssa.cssabackend.util.UrlChecker;

@Service
public class SponsorService {

    private final SponsorRepository sponsorRepository;

    public SponsorService(SponsorRepository sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    // Create single
    public Sponsor createSponsor(String sponsorName, String coopDuration, String sponsorImageUrl,
            String sponsorWebsiteUrl, String sponsorClass) throws IllegalArgumentException, IOException {
        // Null check
        if (sponsorName == null || sponsorName.isEmpty()) {
            throw new IllegalArgumentException("Sponsor name cannot be null or empty");
        }

        if (coopDuration == null || coopDuration.isEmpty()) {
            throw new IllegalArgumentException("Coop duration cannot be null or empty");
        }

        if (sponsorImageUrl == null || sponsorImageUrl.isEmpty()) {
            throw new IllegalArgumentException("Sponsor image url cannot be null or empty");
        }

        if (sponsorWebsiteUrl == null || sponsorWebsiteUrl.isEmpty()) {
            throw new IllegalArgumentException("Sponsor website url cannot be null or empty");
        }

        if (sponsorClass == null || sponsorClass.isEmpty()) {
            throw new IllegalArgumentException("Sponsor class cannot be null or empty");
        }

        // duplicate check
        if (sponsorRepository.findSponsorByName(sponsorName).isPresent()) {
            throw new IllegalArgumentException("Sponsor name already exists");
        }

        // url avilability check
        try {
            UrlChecker urlChecker = UrlChecker.isValidUrl(sponsorImageUrl);
            if (!urlChecker.isValid()) {
                throw new IllegalArgumentException("Sponsor image url connection failed");
            }
            sponsorImageUrl = urlChecker.getUrl();
        } catch (Exception e) {
            throw new IllegalArgumentException("Sponsor image url format error");
        }
        try {
            UrlChecker urlChecker = UrlChecker.isValidUrl(sponsorWebsiteUrl);
            if (!urlChecker.isValid()) {
                throw new IllegalArgumentException("Sponsor website url connection failed");
            }
            sponsorWebsiteUrl = urlChecker.getUrl();
        } catch (Exception e) {
            throw new IllegalArgumentException("Sponsor website url format error");
        }

        CoopDuration coopDurationEnum;
        try {
            coopDurationEnum = CoopDuration.valueOf(coopDuration);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Coop duration is not valid");
        }

        SponsorClass sponsorClassEnum;
        try {
            sponsorClassEnum = SponsorClass.valueOf(sponsorClass);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Sponsor class is not valid");
        }

        Sponsor sponsor = new Sponsor(sponsorName, coopDurationEnum, sponsorImageUrl, sponsorWebsiteUrl,
                sponsorClassEnum);
        return sponsorRepository.createSponsor(sponsor);
    }

    // Find single
    public Optional<Sponsor> findSponsorByName(String sponsorName) {
        return sponsorRepository.findSponsorByName(sponsorName);
    }

    // Find many
    public List<Sponsor> findAllSponsors() {
        return sponsorRepository.findAllSponsors();
    }

    public List<Sponsor> findSponsorsByClass(String sponsorClass) {
        if (!sponsorClass.equals("PLATINUM") && !sponsorClass.equals("GOLD") && !sponsorClass.equals("SILVER")) {
            throw new IllegalArgumentException("Sponsor class is not valid");
        }
        return sponsorRepository.findSponsorsByClass(sponsorClass);
    }

    public List<Sponsor> findSponsorsByCoopDuration(String coopDuration) {
        if (!coopDuration.equals("QUARTER_YEAR") && !coopDuration.equals("FULL_YEAR")) {
            throw new IllegalArgumentException("Coop duration is not valid");
        }
        return sponsorRepository.findSponsorsByCoopDuration(coopDuration);
    }

    // Delete single
    public boolean deleteSponsorByName(String sponsorName) {
        return sponsorRepository.deleteSponsorByName(sponsorName);
    }

    // Update single
    public boolean updateSponsor(String sponsorName, String coopDuration, String sponsorImageUrl,
            String sponsorWebsiteUrl, String sponsorClass) throws IOException {
        // object availavility check
        if (!sponsorRepository.findSponsorByName(sponsorName).isPresent()) {
            throw new IllegalArgumentException("Sponsor does not exist");
        }

        // If all fields are null, throw exception
        if ((coopDuration == null || coopDuration.isEmpty()) && (sponsorImageUrl == null || sponsorImageUrl.isEmpty())
                && (sponsorWebsiteUrl == null || sponsorWebsiteUrl.isEmpty())
                && (sponsorClass == null || sponsorClass.isEmpty())) {
            throw new IllegalArgumentException("Nothing to be changed");
        }

        // parameter check
        if (coopDuration != null && !coopDuration.isEmpty() && !coopDuration.equals("QUARTER_YEAR")
                && !coopDuration.equals("FULL_YEAR")) {
            throw new IllegalArgumentException("Coop duration is not valid");
        }

        if (sponsorClass != null && !sponsorClass.isEmpty() && (!sponsorClass.equals("PLATINUM")
                && !sponsorClass.equals("GOLD") && !sponsorClass.equals("SILVER"))) {
            throw new IllegalArgumentException("Sponsor class is not valid");
        }

        try {
            if (sponsorImageUrl != null && !sponsorImageUrl.isEmpty()) {
                UrlChecker urlChecker = UrlChecker.isValidUrl(sponsorImageUrl);
                if (!urlChecker.isValid()) {
                    throw new IllegalArgumentException("Sponsor image url connection failed");
                }
                sponsorImageUrl = urlChecker.getUrl();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Sponsor image url format error");
        }

        try {
            if (sponsorWebsiteUrl != null && !sponsorWebsiteUrl.isEmpty()) {
                UrlChecker urlChecker = UrlChecker.isValidUrl(sponsorWebsiteUrl);
                if (!urlChecker.isValid()) {
                    throw new IllegalArgumentException("Sponsor website url connection failed");
                }
                sponsorWebsiteUrl = urlChecker.getUrl();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Sponsor website url format error");
        }

        return sponsorRepository.updateSponsor(sponsorName, coopDuration, sponsorImageUrl, sponsorWebsiteUrl,
                sponsorClass);
    }
}
