package com.domanov.vaadin.service;

import com.domanov.vaadin.dto.AuthResponse;
import com.domanov.vaadin.dto.RegistrationRequest;
import com.domanov.vaadin.dto.UserResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service("VaadinService")
public class VaadinService {

    private static final String AUTH = "http://localhost:8084/api/v1/authenticate";

    private static final String MUSEUM = "http://localhost:8080/api/v1/museums";

    @Autowired
    private SessionClient sessionClient;

    public Object authenticate(String username, String password) {
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


            AuthResponse authResponse = new AuthResponse();
            authResponse.setJwt(jsonNode.get("jwt").asText());
            if (!authResponse.getJwt().equals("null")) {
                Cookie myCookie = new Cookie("jwt", authResponse.getJwt());
                myCookie.setMaxAge(10 * 60); // define after how many *seconds* the cookie should expire
                myCookie.setPath("/"); // single slash means the cookie is set for your whole application.
                VaadinResponse.getCurrent().addCookie(myCookie);
                return authResponse;
            } else {
                return false;
            }
        }
        catch (Exception e) {
            return false;
        }
    }

    public Object getAllMuseums() throws IOException, InterruptedException {
        String jwt = "";
        Cookie[] cookies = VaadinRequest.getCurrent().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                jwt = cookie.getValue();
            }
        }
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .header("Content-Type", "application/json")
                .header("jwt", jwt)
                .uri(URI.create(MUSEUM))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return null;
    }

    public Object registration(String login,String name,String surname,String password) {
        try {
            RegistrationRequest registrationRequest = new RegistrationRequest();
            registrationRequest.setLogin(login);
            registrationRequest.setName(name);
            registrationRequest.setSurname(surname);
            registrationRequest.setPassword(password);
            sessionClient.registration(registrationRequest);
            return true;
        } catch (Exception e) {
            return e;
        }
    }
}
