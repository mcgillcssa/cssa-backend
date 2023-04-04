package ca.mcgillcssa.cssabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CssaBackendApplication {

  public static void main(String[] args) {
    try {
      SpringApplication app = new SpringApplication(CssaBackendApplication.class);
      app.run(args);
      System.out.println("Backend now running on port 8080!");
    } catch (Exception e) {
      System.err.println("An error occurred while running the application:");
      e.printStackTrace(System.err);
    }
  }
}
