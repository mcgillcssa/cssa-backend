package ca.mcgillcssa.cssabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import ca.mcgillcssa.cssabackend.model.Sponsor;

@Data
@AllArgsConstructor
public class SponsorDTO {
    private String sponsorName;
    private String coopDuration;
    private String sponsorImageUrl;
    private String sponsorWebsiteUrl;
    private String sponsorClass;

    public SponsorDTO(Sponsor sponsor) {
        this.sponsorName = sponsor.getSponsorName();
        this.coopDuration = sponsor.getCoopDuration().toString();
        this.sponsorImageUrl = sponsor.getSponsorImageUrl();
        this.sponsorWebsiteUrl = sponsor.getSponsorWebsiteUrl();
        this.sponsorClass = sponsor.getSponsorClass().toString();
    }
}
