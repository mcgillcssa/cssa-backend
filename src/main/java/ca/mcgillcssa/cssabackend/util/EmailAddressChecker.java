package ca.mcgillcssa.cssabackend.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailAddressChecker {
  private static final Pattern PERSONAL_EMAIL_PATTERN = Pattern.compile(
      "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");

  private static final Pattern SCHOOL_EMAIL_PATTERN = Pattern.compile(
      "^[a-zA-Z0-9._%+-]+@mail\\.mcgill\\.ca$");

  public static boolean isValidPersonalEmail(String email) {
    Matcher matcher = PERSONAL_EMAIL_PATTERN.matcher(email);
    return matcher.matches();
  }

  public static boolean isValidSchoolEmail(String email) {
    Matcher matcher = SCHOOL_EMAIL_PATTERN.matcher(email);
    return matcher.matches();
  }
}
