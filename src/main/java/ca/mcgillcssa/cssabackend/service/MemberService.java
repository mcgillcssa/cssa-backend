package ca.mcgillcssa.cssabackend.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ca.mcgillcssa.cssabackend.model.Member;
import ca.mcgillcssa.cssabackend.repository.MemberRepository;
import ca.mcgillcssa.cssabackend.util.EmailAddressChecker;

@Service
public class MemberService {

  private final MemberRepository memberRepository;

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public Member createMember(String name, String pseudo, String personalEmail, String schoolEmail, String wechatId,
      String caPhoneNum, String cnPhoneNum, String birthdayStr,
      String departmentStr, String positionStr, String clothSizeStr) throws IllegalArgumentException {

    if (name == null || personalEmail == null || schoolEmail == null || birthdayStr == null || departmentStr == null
        || positionStr == null) {
      throw new IllegalArgumentException(
          "Missing information: name, personal email, mcgill email, birthday, department and position are required.");
    }

    if (findByPersonalEmail(personalEmail).isPresent()) {
      throw new IllegalArgumentException("A member with the personal email " +
          personalEmail + " already exists.");
    }

    if (findBySchoolEmail(personalEmail).isPresent()) {
      throw new IllegalArgumentException("A member with the school email " +
          personalEmail + " already exists.");
    }

    if (!EmailAddressChecker.isValidPersonalEmail(personalEmail)) {
      throw new IllegalArgumentException(personalEmail + "is not a valid personal email");
    }

    if (!EmailAddressChecker.isValidSchoolEmail(schoolEmail)) {
      throw new IllegalArgumentException(schoolEmail + "is not a valid McGill email");
    }

    LocalDate birthday;
    try {
      birthdayStr = "2000-" + birthdayStr;
      birthday = LocalDate.parse(birthdayStr);
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Please provide a valid birthday in the format of MM-dd.");
    }

    Member.Department department;
    try {
      department = Member.Department.valueOf(departmentStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("No department with name " + departmentStr + ".");
    }

    Member.Position position;
    try {
      position = Member.Position.valueOf(positionStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("No position with name " + positionStr + ".");
    }

    Member.ClothSize clothSize;
    try {
      clothSize = Member.ClothSize.valueOf(clothSizeStr.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(clothSizeStr + " is not a valid clothing size.");
    }

    Member newMember = new Member(name, pseudo, personalEmail, schoolEmail, wechatId, caPhoneNum, cnPhoneNum, birthday,
        department, position, clothSize);

    return memberRepository.createMember(newMember);
  }

  public Optional<Member> findByPersonalEmail(String personalEmail) {
    return memberRepository.findByPersonalEmail(personalEmail);
  }

  public Optional<Member> findBySchoolEmail(String personalEmail) {
    return memberRepository.findBySchoolEmail(personalEmail);
  }

  public boolean deleteByPersonalEmail(String personalEmail) {
    return memberRepository.deleteByPersonalEmail(personalEmail);
  }

  public boolean deleteBySchoolEmail(String schoolEmail) {
    return memberRepository.deleteBySchoolEmail(schoolEmail);
  }
}