package ca.mcgillcssa.cssabackend.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Document(collection = "registrations")
@Data
@AllArgsConstructor
@ToString
public class Registration {
  @Id
  private String name;
  private String ticketName;
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
