package ca.mcgillcssa.cssabackend.dto;

import java.time.format.DateTimeFormatter;

import ca.mcgillcssa.cssabackend.model.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDTO {
  private String name;
  private String pseudo;
  private String personalEmail;
  private String schoolEmail;
  private String wechatId;
  private String caPhoneNum;
  private String cnPhoneNum;
  private String birthDay; // represented as String in MM-dd format
  private String department;
  private String position;
  private String clothSize;

  public MemberDTO(Member member) {
    this.name = member.getName();
    this.pseudo = member.getPseudo();
    this.personalEmail = member.getPersonalEmail();
    this.schoolEmail = member.getSchoolEmail();
    this.wechatId = member.getWechatId();
    this.caPhoneNum = member.getCaPhoneNum();
    this.cnPhoneNum = member.getCnPhoneNum();
    this.birthDay = member.getBirthDay().format(DateTimeFormatter.ofPattern("MM-dd"));
    this.department = member.getDepartment().toString();
    this.position = member.getPosition().toString();
    this.clothSize = member.getClothSize().toString();
  }
}
