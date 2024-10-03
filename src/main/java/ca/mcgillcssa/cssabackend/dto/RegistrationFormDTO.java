package ca.mcgillcssa.cssabackend.dto;

import ca.mcgillcssa.cssabackend.model.RegistrationForm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationFormDTO {
  private String name;
  private String email;
  private String wechatId;
  private String registrationDate;
  private String registrationType;

  public RegistrationFormDTO(RegistrationForm registrationform) {
    this.name = registrationform.getName();
    this.email = registrationform.getEmail();
    this.wechatId = registrationform.getWechatId();
    this.registrationDate = registrationform.getRegistrationDate().toString();
    this.registrationType = registrationform.getRegistrationType().toString();
  }
}

