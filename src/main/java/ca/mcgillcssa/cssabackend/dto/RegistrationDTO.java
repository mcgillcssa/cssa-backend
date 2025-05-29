package ca.mcgillcssa.cssabackend.dto;

import ca.mcgillcssa.cssabackend.model.Registration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationDTO {
  private String name;
  private String ticketName;
  private String email;
  private String wechatId;
  private String registrationDate;
  private String registrationType;

  public RegistrationDTO(Registration registration) {
    this.name = registration.getName();
    this.ticketName = registration.getTicketName();
    this.email = registration.getEmail();
    this.wechatId = registration.getWechatId();
    this.registrationDate = registration.getRegistrationDate().toString();
    this.registrationType = registration.getRegistrationType().toString();
  }
}

