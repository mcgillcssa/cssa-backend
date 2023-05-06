package ca.mcgillcssa.cssabackend.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Document(collection = "members")
@Data
@AllArgsConstructor
@ToString
public class Member {
  private String name;
  private String pseudo;
  private String personalEmail;
  @Id
  private String schoolEmail;
  private String wechatId;
  private String caPhoneNum;
  private String cnPhoneNum;
  private LocalDate birthday;
  private Department department;
  private Position position;
  private ClothSize clothSize;

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
    ADVISOR;

    @Override
    public String toString() {
      return name();
    }
  }
}
