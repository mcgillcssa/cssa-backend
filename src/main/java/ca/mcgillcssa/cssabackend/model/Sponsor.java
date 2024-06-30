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
  private SponsorClass sponsorClass;
  private String sponsorDescription;

  public enum CoopDuration {
    QUARTER_YEAR,
    FULL_YEAR;

    @Override
    public String toString() {
      return name();
    }
  }

  public enum SponsorClass {
    DIAMOND_EXCLUSIVE,
    DIAMOND,
    GOLD;

    @Override
    public String toString() {
      return name();
    }
  }
}
