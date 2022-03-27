package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.UserResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.http.ResponseEntity;
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
        userResponse.setTelephoneNumber(jsonNode.get("telephoneNumber").asText());
        userResponse.setUser_uid(UUID.fromString(jsonNode.get("user_uid").asText()));
        return userResponse;
    }
}
