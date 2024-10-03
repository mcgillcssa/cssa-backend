package ca.mcgillcssa.cssabackend.model;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Document(collection = "registrationforms")
@Data
@ToString
public class RegistrationForm {
  private String name;
  private String email;
  private String wechatId;
  private LocalDate registrationDate;
  private RegistrationType registrationType;

  public enum RegistrationType {
    EARLYBIRD,
    REGULAR;

    @Override
    public String toString() {
      return name();
    }
  }

}
