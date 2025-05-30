package ca.mcgillcssa.cssabackend.dto;

import java.time.LocalDate;

import ca.mcgillcssa.cssabackend.model.Registration;
import ca.mcgillcssa.cssabackend.model.Registration.RegistrationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationDTO {
  private String name;
  private String ticketName;
  private String email;
  private String wechatId;
  private LocalDate registrationDate;
  private RegistrationType registrationType;

  public RegistrationDTO(Registration registration) {
    this.name = registration.getName();
    this.ticketName = registration.getTicketName();
    this.email = registration.getEmail();
    this.wechatId = registration.getWechatId();
    this.registrationDate = registration.getRegistrationDate();
    this.registrationType = registration.getRegistrationType();
  }
}

