package ca.mcgillcssa.cssabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CssaBackendApplication {

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(CssaBackendApplication.class);
    app.addListeners(new ApplicationListener<ApplicationReadyEvent>() {
      @Override
      public void onApplicationEvent(ApplicationReadyEvent event) {
        String port = event.getApplicationContext().getEnvironment().getProperty("server.port");
        System.out.println("Backend now running on port " + port + "!");
      }
    });
    app.run(args);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("http://localhost:3000", "https://www.mcgillcssa.ca");
      }
    };
  }
}
