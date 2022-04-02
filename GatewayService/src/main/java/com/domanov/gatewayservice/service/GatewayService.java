package com.domanov.gatewayservice.service;

import com.domanov.gatewayservice.dto.MuseumPageResponse;
import com.domanov.gatewayservice.dto.UserResponse;
import com.domanov.gatewayservice.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service("GatewayService")
public class GatewayService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private MuseumClient museumClient;

    @Autowired
    private SessionClient sessionClient;

    public ResponseEntity<MuseumPageResponse> getMuseums(String jwt, int page, int size) {
        try {
            if (validateToken(jwt) != null) {
//                MuseumPageResponse museumPageResponse = museumClient.getMuseums(page, size);
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
            String login = validateToken(jwt);
            if (login != null) {
                return new ResponseEntity<>(sessionClient.getUser(login), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new UserResponse(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new UserResponse(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private String validateToken(String jwt) {
        try {
            jwt = jwt.substring(7);
            String login = jwtTokenUtil.getUsernameFromToken(jwt);
            if (jwtTokenUtil.validateToken(jwt, login)) {
                return login;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
