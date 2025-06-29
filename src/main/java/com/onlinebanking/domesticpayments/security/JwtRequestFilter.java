package com.onlinebanking.domesticpayments.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
/*
Intercepts incoming requests
Validates JWT tokens with an external service (hosted on AWS
Extracts user details and roles from the token
Sets up Spring Security authentication
Makes claims available to controllers via request attributes
Implements role-based access control using GrantedAuthority
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    //JWT validation API URL from application properties
    @Value("${jwt.validation.api.url}")
    private String jwtValidationApiUrl;

    private final JwtUtil jwtUtil;
    //RestTemplate for making HTTP requests to the JWT validation API
    private final RestTemplate restTemplate;
    //constructor
    public JwtRequestFilter(JwtUtil jwtUtil, RestTemplate restTemplate) {
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplate;
    }

    /*
    Intercepts each request to validate JWT tokens
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("JwtRequestFilter: doFilterInternal called");
        // Check for Authorization header and extract JWT token
        final String authHeader = request.getHeader("Authorization");

        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("JwtRequestFilter: Authorization header found");
            jwt = authHeader.substring(7);

            // Prepare headers to send to JWT validation API
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authHeader);
            headers.set("X-OB-E2E-Token", jwt);

            // Prepare request entity
            HttpEntity<String> entity = new HttpEntity<>(headers);
            System.out.println("JwtRequestFilter: Prepared HttpEntity with Authorization header: " + headers);

            try {
                // Call JWT validation API using RestTemplate, the response is stored in a Map
                ResponseEntity<Map> responseEntity = restTemplate.exchange(
                        jwtValidationApiUrl,
                        HttpMethod.GET,
                        entity,
                        Map.class);

                // Check if the response is successful from the JWT validation API
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    Map<String, Object> body = responseEntity.getBody();

                    System.out.println("JwtRequestFilter: JWT validation response: " + body);
                    //boolean isValid = (boolean) body.get("message");
                    assert body != null;

                    //Decode the JWT token from Base64, as it is encoded in Base64 format from request
                    String based64DecodedJwt = new String(Base64.getDecoder().decode(jwt));
                    // Check if the response body contains a valid token message
                    boolean isValid = body.toString().equals("{message=Token valid}") && this.jwtUtil.validateToken(based64DecodedJwt);

                    //if the token is valid, extract username and claims
                    if (isValid) {
                        // Extract username from the decoded JWT token
                        //get claims from the decoded JWT token
                        Jws<Claims> jws = jwtUtil.getClaims(based64DecodedJwt);
                        Claims claims = jws.getPayload();
                        String subject = (String) claims.get("sub");
                        System.out.println("JwtRequestFilter: JWT subject: " + subject);

                        // If subject is not null and there is no existing authentication in the security context
                        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                            // Create authorities based on claims
                            List<GrantedAuthority> authorities = new ArrayList<>();

                            // Add role from claims
                            String role = (String) claims.get("role");
                            if (role != null) {
                                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
                            }
                            UsernamePasswordAuthenticationToken authToken =
                                    new UsernamePasswordAuthenticationToken(
                                            new User(subject, "", authorities),
                                            null,
                                            authorities);

                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                            // Add claims to request attributes for controller access
                            request.setAttribute("jwt_claims", claims);
                            request.setAttribute("user_id", claims.get("username", String.class));
                            request.setAttribute("email", claims.get("email", String.class));
                        }
                    }

                } else {
                    // Handle validation failure
                    logger.warn("JWT validation failed: " + responseEntity.getStatusCode());
                }

            } catch (Exception e) {
                logger.error("Error validating JWT", e);
            }
        }

        chain.doFilter(request, response);
    }
}