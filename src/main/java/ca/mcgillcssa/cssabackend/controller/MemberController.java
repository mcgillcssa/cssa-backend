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

import ca.mcgillcssa.cssabackend.dto.MemberDTO;
import ca.mcgillcssa.cssabackend.model.Member;
import ca.mcgillcssa.cssabackend.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/")
  public ResponseEntity<?> createMember(@RequestBody MemberRequestBody requestBody) {
    try {
      Member newMember = memberService.createMember(requestBody.getName(), requestBody.getPseudo(),
          requestBody.getPersonalEmail(), requestBody.getSchoolEmail(), requestBody.getWechatId(),
          requestBody.getCaPhoneNum(), requestBody.getCnPhoneNum(), requestBody.getBirthDay(),
          requestBody.getDepartment(), requestBody.getPosition(), requestBody.getClothSize());

      return ResponseEntity.ok(new MemberDTO(newMember));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
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

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public class MemberRequestBody {
    private String name;
    private String pseudo;
    private String personalEmail;
    private String schoolEmail;
    private String wechatId;
    private String caPhoneNum;
    private String cnPhoneNum;
    private String birthDay;
    private String department;
    private String position;
    private String clothSize;
  }
}
