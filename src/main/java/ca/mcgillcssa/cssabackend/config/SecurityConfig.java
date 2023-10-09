package ca.mcgillcssa.cssabackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import ca.mcgillcssa.cssabackend.service.JwtService;
import ca.mcgillcssa.cssabackend.util.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class SecurityConfig {

  @Autowired
  private JwtService jwtService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeHttpRequests()
        .requestMatchers("/auth/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/**").authenticated() // authenticate all
        // POST requests
        .requestMatchers(HttpMethod.PUT, "/**").authenticated() // authenticate all PUT requests
        .requestMatchers(HttpMethod.DELETE, "/**").authenticated() // authenticate all DELETE requests
        .anyRequest().permitAll() // all other requests (e.g., GET) are permitted without authentication
        .and()
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
    return new JwtAuthenticationFilter(jwtService);
  }
}
