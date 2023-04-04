package ca.mcgillcssa.cssabackend.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgillcssa.cssabackend.model.Member;
import ca.mcgillcssa.cssabackend.service.MemberService;

@RestController
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/")
  public ResponseEntity<Member> createMember(@RequestBody MemberRequestBody requestBody) {
    Member newMember = memberService.createMember(requestBody.getName(), requestBody.getPersonalEmail(),
        requestBody.getBirthDay(), requestBody.getDepartment(), requestBody.getPosition());
    return ResponseEntity.ok(newMember);
  }

  @GetMapping("/{personalEmail}")
  public ResponseEntity<Member> getMemberByPersonalEmail(@PathVariable String personalEmail) {
    Optional<Member> optionalMember = memberService.findByPersonalEmail(personalEmail);
    return optionalMember.map(member -> ResponseEntity.ok(member))
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{personalEmail}")
  public ResponseEntity<?> deleteMemberByPersonalEmail(@PathVariable String personalEmail) {
    memberService.deleteByPersonalEmail(personalEmail);
    return ResponseEntity.noContent().build();
  }

  public static class MemberRequestBody {
    private String name;
    private String personalEmail;
    private String birthDay;

    private String department;
    private String position;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPersonalEmail() {
      return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
      this.personalEmail = personalEmail;
    }

    public String getBirthDay() {
      return birthDay;
    }

    public void setBirthDay(String birthDay) {
      this.birthDay = birthDay;
    }

    public String getDepartment() {
      return department;
    }

    public void setDepartment(String department) {
      this.department = department;
    }

    public String getPosition() {
      return position;
    }

    public void setPosition(String position) {
      this.position = position;
    }
  }
}
