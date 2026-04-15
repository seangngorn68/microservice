package com.example.auth_service.service;

import com.example.auth_service.entity.LoginRequest;
import com.example.auth_service.entity.RegisterRequest;
import com.example.auth_service.entity.User;
import com.example.auth_service.repo.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakUserService {

    public UserRepository userRepository;

    private final RestTemplate restTemplate = new RestTemplate();

   // http://localhost:8181/realms/microservices-realm/protocol/openid-connect/token

    private static final String secretKey = "Zmtp7cKKlBmAiZksLtdKfAYInKMcAoPO";

    private static final String TOKEN_URL = "http://localhost:8181/realms/microservices-realm/protocol/openid-connect/token";
    public KeycloakUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Map<String, Object> login(LoginRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        body.add("client_id", "auth-service-client");
        body.add("client_secret", secretKey);
        body.add("grant_type", "password");
        body.add("username", request.getUsername());
        body.add("password", request.getPassword());
        body.add("scope", "openid");


        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response =
                    restTemplate.postForEntity(TOKEN_URL, entity, Map.class);

            return response.getBody();

        } catch (HttpClientErrorException e) {
            System.out.println("STATUS: " + e.getStatusCode());
            System.out.println("ERROR BODY: " + e.getResponseBodyAsString());
            throw e;
        }
    }
    public void createUser(RegisterRequest req) {

        String token = getAdminToken();

        System.out.println("Token::::"+token);

        String url = "http://localhost:8181/admin/realms/microservices-realm/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String, Object> body = new HashMap<>();
        body.put("username", req.getUsername());
        body.put("email", req.getEmail());
        body.put("enabled", true);

        // 🔥 ADD PASSWORD (IMPORTANT)
        Map<String, Object> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", req.getPassword());
        credential.put("temporary", false); // MUST be false

        body.put("credentials", List.of(credential));



        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(url, entity, String.class);

        // Save to DB
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());

        userRepository.save(user);
    }

    private String getAdminToken() {

        String url = "http://localhost:8181/realms/master/protocol/openid-connect/token";

        System.out.println("Token123::::");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "admin-cli");
        body.add("username", "admin");
        body.add("password", "admin");
        body.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(url, request, Map.class);

        return response.getBody().get("access_token").toString();
    }

}
