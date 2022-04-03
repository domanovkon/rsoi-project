package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.MuseumPageResponse;
import com.domanov.gatewayservice.dto.UserResponse;
import com.domanov.gatewayservice.dto.ValidateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service("GatewayService")
public class GatewayService {

    @Autowired
    private MuseumClient museumClient;

    @Autowired
    private SessionClient sessionClient;

    public ResponseEntity<MuseumPageResponse> getMuseums(String jwt, int page, int size) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return new ResponseEntity<>(museumClient.getMuseums(page, size), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<UserResponse> getUser(String jwt) {
        try {
            ValidateToken validateToken = sessionClient.validate(jwt);
            if (validateToken.getLogin() != null) {
                return new ResponseEntity<>(sessionClient.getUser(validateToken.getLogin()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new UserResponse(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
