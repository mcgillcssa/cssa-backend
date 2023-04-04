package ca.mcgillcssa.cssabackend.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.Builder.Default;

@Document(collection = "members")
@Data
@Builder
@ToString
public class Member {
  private String name;

  @Default
  private String pseudo = "";

  private String personalEmail;

  @Default
  private String schoolEmail = "";

  @Default
  private String wechatId = "";

  @Default
  private String caPhoneNum = "";

  @Default
  private String cnPhoneNum = "";

  private LocalDate birthDay;

  @Default
  private ClothSize clothSize = ClothSize.NA;

  private Department department;

  private Position position;

  public enum ClothSize {
    NA,
    XS,
    S,
    M,
    L,
    XL,
    XXL,
    XXXL;

    @Override
    public String toString() {
      return name();
    }
  }

  public enum Department {
    ACADEMIC,
    COMMU,
    EVENT,
    EXTERNAL,
    FINANCE,
    INTERNAL,
    IT,
    MEDIA,
    OTHER;

    @Override
    public String toString() {
      return name();
    }
  }

  public enum Position {
    PRESIDENT,
    VICEPRESIDENT,
    DIRECTOR,
    EXECUTIVE,
    SADVISOR;

    @Override
    public String toString() {
      return name();
    }
  }
}
