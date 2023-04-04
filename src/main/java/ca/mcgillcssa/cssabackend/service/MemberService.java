package ca.mcgillcssa.cssabackend.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ca.mcgillcssa.cssabackend.model.Member;
import ca.mcgillcssa.cssabackend.repository.MemberRepository;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public Member createMember(String name, String personalEmail, String birthDayStr,
      String departmentStr, String positionStr) {
    Member.Department department = Member.Department.valueOf(departmentStr.toUpperCase());
    Member.Position position = Member.Position.valueOf(positionStr.toUpperCase());
    LocalDate birthDay = LocalDate.parse(birthDayStr);
    Member newMember = Member.builder()
        .name(name)
        .personalEmail(personalEmail)
        .birthDay(birthDay)
        .department(department)
        .position(position)
        .build();
    return memberRepository.createMember(newMember);
  }

  public Optional<Member> findByPersonalEmail(String personalEmail) {
    return memberRepository.findByPersonalEmail(personalEmail);
  }

  public void deleteByPersonalEmail(String personalEmail) {
    memberRepository.deleteByPersonalEmail(personalEmail);
  }
}