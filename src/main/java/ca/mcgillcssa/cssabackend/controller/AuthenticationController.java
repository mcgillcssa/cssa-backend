package ca.mcgillcssa.cssabackend.controller;

import ca.mcgillcssa.cssabackend.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private JwtService jwtService;

  @PostMapping("/token")
  public ResponseEntity<?> generateToken(@RequestBody AuthenticationRequest authRequest) {
    try {
      if ("cssa".equals(authRequest.getUsername()) && "ilovecssa".equals(authRequest.getPassword())) {
        String token = jwtService.generateToken(authRequest.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(token);
      } else {
        return ResponseEntity.badRequest().body("Invalid credentials");
      }
    } catch (Exception e) {
      // log the error for debugging
      e.printStackTrace(); // This logs the error in the console; in a real-world application, you might
                           // use a logger.
      // return a descriptive error message
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating token: " + e.getMessage());
    }
  }

  // Request object
  public static class AuthenticationRequest {
    public String username;
    public String password;

    // getters, setters, constructors...
    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }
}
