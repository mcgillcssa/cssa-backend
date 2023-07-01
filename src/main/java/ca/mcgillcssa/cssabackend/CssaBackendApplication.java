package ca.mcgillcssa.cssabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000");
      }
    };
  }
}
