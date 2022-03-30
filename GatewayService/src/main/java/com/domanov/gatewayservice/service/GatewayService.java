package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.UserResponse;
import com.domanov.gatewayservice.utils.JwtTokenUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

@Service("GatewayService")
public class GatewayService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MuseumClient museumClient;

    private static final String AUTH = "http://localhost:8084/api/v1/authenticate";

    public UserResponse getUser(String username, String password) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        String usernameUriString = UriComponentsBuilder.fromUriString(AUTH).queryParam("username", username).toUriString();
        String passwordUriString = UriComponentsBuilder.fromUriString(usernameUriString).queryParam("password", password).toUriString();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(passwordUriString))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper1 = new ObjectMapper();
        objectMapper1.findAndRegisterModules();
        JsonNode jsonNode = objectMapper1.readTree(response.body());

        UserResponse userResponse = new UserResponse();
        userResponse.setName(jsonNode.get("name").asText());
        userResponse.setLogin(jsonNode.get("login").asText());
        userResponse.setRole(jsonNode.get("role").asText());
        userResponse.setUser_uid(UUID.fromString(jsonNode.get("user_uid").asText()));
        return userResponse;
    }

    public Object getAllMuseums(String jwt) {
        String login = jwtTokenUtil.getUsernameFromToken(jwt);
        if (login != null && jwtTokenUtil.validateToken(jwt, login)) {
            museumClient.getMuseums();
            System.out.println("123");
        } else {
            System.out.println("456");
        }
        return true;
    }
}
