package ca.mcgillcssa.cssabackend.controller;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
import lombok.Data;
import lombok.ToString;

@RestController
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/")
  public ResponseEntity<?> createMember(@RequestBody MemberRequestBody requestBody) {
    Map<String, Object> response = new HashMap<>();
    try {
      Member newMember = memberService.createMember(requestBody.getName(), requestBody.getPseudo(),
          requestBody.getPersonalEmail(), requestBody.getSchoolEmail(), requestBody.getWechatId(),
          requestBody.getCaPhoneNum(), requestBody.getCnPhoneNum(), requestBody.getBirthday(),
          requestBody.getDepartment(), requestBody.getPosition(), requestBody.getClothSize());

      response.put("message", "Member created");
      response.put("member", new MemberDTO(newMember));
      return ResponseEntity.status(HttpStatus.OK).body(response);

    } catch (IllegalArgumentException e) {
      response.put("message", "Failed to create member.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

    } catch (DataAccessException e) {
      response.put("message", "Failed to create member.");
      response.put("errorDetails", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  @GetMapping("/personal/{personalEmail}")
  public ResponseEntity<?> getMemberByPersonalEmail(@PathVariable String personalEmail) {
    Optional<Member> optionalMember = memberService.findByPersonalEmail(personalEmail);
    Map<String, Object> response = new HashMap<>();
    if (optionalMember.isPresent()) {
      Member member = optionalMember.get();
      response.put("message", "Member found with personal email " + personalEmail);
      response.put("member", new MemberDTO(member));
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "Member not found with personal email " + personalEmail);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @GetMapping("/school/{schoolEmail}")
  public ResponseEntity<?> getMemberBySchoolEmail(@PathVariable String schoolEmail) {
    Optional<Member> optionalMember = memberService.findBySchoolEmail(schoolEmail);
    Map<String, Object> response = new HashMap<>();
    if (optionalMember.isPresent()) {
      Member member = optionalMember.get();
      response.put("message", "Member found with mcgill email " + schoolEmail);
      response.put("member", new MemberDTO(member));
      return ResponseEntity.status(HttpStatus.OK).body(response);
    } else {
      response.put("message", "Member not found with mcgill email " + schoolEmail);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

  @GetMapping("/birthdayMonth/{birthdayMonth}")
  public ResponseEntity<?> getMembersByBirthMonth(@PathVariable int birthdayMonth) {
    Map<String, Object> response = new HashMap<>();

    List<Member> members = memberService.findByBirthdayMonth(birthdayMonth);
    if (members.size() > 0) {
      List<MemberDTO> memberDTOs = new ArrayList<MemberDTO>();
      for (Member member : members) {
        memberDTOs.add(new MemberDTO(member));
      }
      response.put("message", "Members found with birthday in " + Month.of(birthdayMonth).name());
      response.put("member", memberDTOs);
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    response.put("message", "No member found with birthday in " + Month.of(birthdayMonth).name());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @DeleteMapping("/personal/{personalEmail}")
  public ResponseEntity<?> deleteMemberByPersonalEmail(@PathVariable String personalEmail) {
    Map<String, Object> response = new HashMap<>();
    boolean deleted = memberService.deleteByPersonalEmail(personalEmail);
    if (deleted) {
      response.put("message", "Member with personal email " + personalEmail + " successfully deleted");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    response.put("message", "Member with personal email " + personalEmail + " not found");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @DeleteMapping("/school/{schoolEmail}")
  public ResponseEntity<?> deleteMemberBySchoolEmail(@PathVariable String schoolEmail) {
    Map<String, Object> response = new HashMap<>();
    boolean deleted = memberService.deleteBySchoolEmail(schoolEmail);
    if (deleted) {
      response.put("message", "Member with school email " + schoolEmail + " successfully deleted");
      return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    response.put("message", "Member with school email " + schoolEmail + " not found");
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @Data
  @ToString
  public static class MemberRequestBody {
    private String name;
    private String pseudo = "";
    private String personalEmail;
    private String schoolEmail;
    private String wechatId = "";
    private String caPhoneNum = "";
    private String cnPhoneNum = "";
    private String birthday;
    private String department;
    private String position;
    private String clothSize = "M";
  }
}
