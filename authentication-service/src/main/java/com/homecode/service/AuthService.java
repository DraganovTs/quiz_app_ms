package com.homecode.service;

import com.homecode.model.AuthRequest;
import com.homecode.model.AuthResponse;
import com.homecode.model.UserDTO;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthService(RestTemplate restTemplate, JwtUtil jwtUtil) {
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
    }


    public AuthResponse register(AuthRequest request) {
        try {
            // ToDo validation if user exists in DB
            request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
            UserDTO registeredUser = restTemplate.postForObject("http://localhost:9002/users/register", request, UserDTO.class);

            String accessToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "ACCESS");
            String refreshToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "REFRESH");

            return new AuthResponse(accessToken, refreshToken);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // You might want to throw a custom exception or return an error response here
            return null;
        }
    }
}
