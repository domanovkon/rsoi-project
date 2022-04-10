package com.domanov.vaadin.service;

import com.domanov.vaadin.dto.*;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service("VaadinService")
public class VaadinService {

    @Autowired
    private SessionClient sessionClient;

    @Autowired
    private GatewayClient gatewayClient;

    public ResponseEntity<AuthResponse> authenticate(String username, String password) {
        try {
            ResponseEntity<AuthResponse> response = sessionClient.authorization(username, password);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                AuthResponse authResponse = response.getBody();
                Cookie myCookie = new Cookie("jwt", authResponse.getJwt());
                myCookie.setMaxAge(10 * 60);
                myCookie.setPath("/");
                VaadinResponse.getCurrent().addCookie(myCookie);
                return response;
            } else {
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<AuthResponse> registration(String login, String name, String surname, String password) {
        try {
            RegistrationRequest registrationRequest = new RegistrationRequest();
            registrationRequest.setLogin(login);
            registrationRequest.setName(name);
            registrationRequest.setSurname(surname);
            registrationRequest.setPassword(password);
            ResponseEntity<AuthResponse> response = sessionClient.registration(registrationRequest);
            if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                AuthResponse authResponse = response.getBody();
                Cookie myCookie = new Cookie("jwt", authResponse.getJwt());
                myCookie.setMaxAge(10 * 60);
                myCookie.setPath("/");
                VaadinResponse.getCurrent().addCookie(myCookie);
                return response;
            }
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity userCheck(String username) {
        return sessionClient.userCheck(username);
    }

    public ResponseEntity<MuseumPageResponse> getMuseums(int page, int size) {
        try {
            return gatewayClient.getMuseums(getJWT(), page, size);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private String getJWT() {
        StringBuilder jwt = new StringBuilder("Bearer ");
        Cookie[] cookies = VaadinRequest.getCurrent().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                jwt.append(cookie.getValue());
            }
        }
        return jwt.toString();
    }

    public ResponseEntity<UserResponse> getUser() {
        try {
            return gatewayClient.getUser(getJWT());
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<MuseumInfoResponse> getMuseumInfo(String museum_uid) {
        try {
            return gatewayClient.getMuseumInfo(getJWT(), museum_uid);
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Object> buyTicket(TicketBuyRequest ticketBuyRequest) {
        try {
            return gatewayClient.buyTicket(getJWT(), ticketBuyRequest);
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
