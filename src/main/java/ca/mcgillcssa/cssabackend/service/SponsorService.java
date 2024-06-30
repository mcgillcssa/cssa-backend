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

    public Sponsor createSponsor(String sponsorName, String coopDuration, String sponsorImageUrl,
            String sponsorClass, String sponsorDescription) throws IllegalArgumentException, IOException {
        if (sponsorName == null || sponsorName.isEmpty()) {
            throw new IllegalArgumentException("Sponsor name cannot be null or empty");
        }

        if (coopDuration == null || coopDuration.isEmpty()) {
            throw new IllegalArgumentException("Coop duration cannot be null or empty");
        }

        if (sponsorImageUrl == null || sponsorImageUrl.isEmpty()) {
            throw new IllegalArgumentException("Sponsor image url cannot be null or empty");
        }

        if (sponsorClass == null || sponsorClass.isEmpty()) {
            throw new IllegalArgumentException("Sponsor class cannot be null or empty");
        }

        if (sponsorDescription == null || sponsorDescription.isEmpty()) {
            throw new IllegalArgumentException("Sponsor description cannot be null or empty");
        }

        if (sponsorRepository.findSponsorByName(sponsorName).isPresent()) {
            throw new IllegalArgumentException("Sponsor name already exists");
        }

        try {
            UrlChecker urlChecker = UrlChecker.isValidUrl(sponsorImageUrl);
            if (!urlChecker.isValid()) {
                throw new IllegalArgumentException("Sponsor image url connection failed");
            }
            sponsorImageUrl = urlChecker.getUrl();
        } catch (Exception e) {
            throw new IllegalArgumentException("Sponsor image url format error");
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


        Sponsor sponsor = new Sponsor(sponsorName, coopDurationEnum, sponsorImageUrl,
                sponsorClassEnum, sponsorDescription);
        return sponsorRepository.createSponsor(sponsor);
    }

    public Optional<Sponsor> findSponsorByName(String sponsorName) {
        return sponsorRepository.findSponsorByName(sponsorName);
    }

    public List<Sponsor> findAllSponsors() {
        return sponsorRepository.findAllSponsors();
    }

    public List<Sponsor> findSponsorsByClass(String sponsorClass) {
        if (!sponsorClass.equals("DIAMOND_EXCLUSIVE") && !sponsorClass.equals("DIAMOND") && !sponsorClass.equals("GOLD")) {
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

    public boolean deleteSponsorByName(String sponsorName) {
        return sponsorRepository.deleteSponsorByName(sponsorName);
    }

    public boolean updateSponsor(String sponsorName, String coopDuration, String sponsorImageUrl,
            String sponsorClass, String sponsorDescription) throws IOException {
        if (!sponsorRepository.findSponsorByName(sponsorName).isPresent()) {
            throw new IllegalArgumentException("Sponsor does not exist");
        }

        if ((coopDuration == null || coopDuration.isEmpty()) && (sponsorImageUrl == null || sponsorImageUrl.isEmpty())
                && (sponsorDescription == null || sponsorDescription.isEmpty())
                && (sponsorClass == null || sponsorClass.isEmpty())) {
            throw new IllegalArgumentException("Nothing to be changed");
        }

        if (coopDuration != null && !coopDuration.isEmpty() && !coopDuration.equals("QUARTER_YEAR")
                && !coopDuration.equals("FULL_YEAR")) {
            throw new IllegalArgumentException("Coop duration is not valid");
        }

        if (sponsorClass != null && !sponsorClass.isEmpty() && (!sponsorClass.equals("DIAMOND_EXCLUSIVE")
                && !sponsorClass.equals("DIAMOND") && !sponsorClass.equals("GOLD"))) {
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

        return sponsorRepository.updateSponsor(sponsorName, coopDuration, sponsorImageUrl,
                sponsorClass, sponsorDescription);
    }
}
