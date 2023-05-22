package ca.mcgillcssa.cssabackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Document(collection = "sponsors")
@Data
@AllArgsConstructor
@ToString

public class Sponsor {
  @Id
  private String sponsorName;
  private CoopDuration coopDuration;
  private String sponsorImageUrl;
  private String sponsorWebsiteUrl;
  private SponsorClass sponsorClass;

  public enum CoopDuration {
    QUARTER_YEAR,
    FULL_YEAR;

    @Override
    public String toString() {
      return name();
    }
  }

  public enum SponsorClass {
    PLATINUM,
    GOLD,
    SILVER;

    @Override
    public String toString() {
      return name();
    }
  }
}
