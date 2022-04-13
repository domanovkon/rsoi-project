package com.domanov.vaadin.service;

import com.domanov.vaadin.dto.*;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

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
                try {
                    ResponseEntity<Object> responseEntity = gatewayClient.addUserRegistrationStat("Bearer " + myCookie.getValue());
                    return response;
                } catch (Exception e) {
                    return response;
                }
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

    public ResponseEntity<TicketListDto> getTicketHistory() {
        try {
            return gatewayClient.getTicketHistory(getJWT());
        } catch (Exception e) {
            return new ResponseEntity<>(new TicketListDto(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Object> changeTheme(Boolean darkTheme) {
        try {
            return gatewayClient.changeTheme(getJWT(), darkTheme);
        } catch (Exception e) {
            return new ResponseEntity<>(new TicketListDto(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<MoneyTransferDto>> getMoneyTransfer() {
        try {
            return gatewayClient.getMoneyTransfer(getJWT());
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<UserStatDto>> getUserStat() {
        try {
            return gatewayClient.getUserStat(getJWT());
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<List<MyseumTypeDto>> getMuseumTypes() {
        try {
            return gatewayClient.getMuseumTypes(getJWT());
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<MuseumInfoResponse> createMuseum(MuseumInfoResponse museumInfoResponse) {
        try {
            return gatewayClient.createMuseum(getJWT(), museumInfoResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Object> removeShow(String show_uid) {
        try {
            return gatewayClient.removeShow(getJWT(), show_uid);
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<Object> removeMuseum(String museum_uid) {
        try {
            return gatewayClient.removeMuseum(getJWT(), museum_uid);
        } catch (Exception e) {
            return new ResponseEntity<>(new MuseumInfoResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<ShowResponse> createShow(ShowResponse showResponse) {
        try {
            return gatewayClient.createShow(getJWT(), showResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(new ShowResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
