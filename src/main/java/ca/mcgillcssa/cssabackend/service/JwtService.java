package ca.mcgillcssa.cssabackend.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtException;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String jwtSecret;

  public String generateToken(String subject) {
    try {
      JwtBuilder builder = Jwts.builder();
      builder.setSubject(subject);
      builder.setExpiration(new Date(System.currentTimeMillis() + 86400000));
      builder.signWith(SignatureAlgorithm.HS512, jwtSecret);
      String tk = builder.compact();
      System.out.println(tk);
      return tk;
    } catch (JwtException e) {

      e.printStackTrace(); // This will print the error stack trace for debugging
      throw new RuntimeException("Error generating JWT token: " + e.getMessage());
    }
  }

  public Claims parseToken(String token) {
    try {
      return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    } catch (JwtException e) {
      e.printStackTrace(); // This will print the error stack trace for debugging
      throw new RuntimeException("Error parsing JWT token: " + e.getMessage());
    }
  }
}
