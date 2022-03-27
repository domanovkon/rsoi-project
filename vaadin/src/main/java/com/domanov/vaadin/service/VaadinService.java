package com.domanov.vaadin.service;

import com.domanov.vaadin.dto.UserResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.attribute.standard.JobKOctets;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Service("VaadinService")
public class VaadinService {

    private static final String AUTH = "http://localhost:8080/api/v1/authenticate";

    public Object authenticate(String username, String password) throws IOException, InterruptedException {
        try {
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
        catch (Exception e) {
            return false;
        }
    }
}
