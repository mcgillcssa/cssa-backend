package ca.mcgillcssa.cssabackend.util;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import ca.mcgillcssa.cssabackend.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  public JwtAuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String token = request.getHeader("Authorization");
    if (token != null && token.startsWith("Bearer")) {
      token = token.substring(7);

      try {
        Claims claims = jwtService.parseToken(token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
            new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
      } catch (JwtException e) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return;
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
